package net.mike_dawson.edtechpreflightchecktool.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import net.mike_dawson.edtechpreflightchecktool.app.FabUiState
import net.mike_dawson.edtechpreflightchecktool.app.OverflowActionBarItem
import net.mike_dawson.edtechpreflightchecktool.app.Snack
import net.mike_dawson.edtechpreflightchecktool.app.SnackBarDispatcher
import net.mike_dawson.edtechpreflightchecktool.datalayer.datasource.PlanDataSource
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostTotals
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.RoiTotal
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Plan
import net.mike_dawson.edtechpreflightchecktool.ext.asUiText
import net.mike_dawson.edtechpreflightchecktool.ext.getAnnualTotals
import net.mike_dawson.edtechpreflightchecktool.ext.sumCostTotals
import net.mike_dawson.edtechpreflightchecktool.nav.NavCommand
import net.mike_dawson.edtechpreflightchecktool.nav.PlanDetailDest
import net.mike_dawson.edtechpreflightchecktool.nav.PlanEditDest
import net.mike_dawson.edtechpreflightchecktool.nav.PlanListDest
import kotlin.uuid.Uuid

data class PlanDetailUiState(
    val plan: Plan? = null,
    val costTotals: Map<String, CostTotals> = emptyMap(),
    val roiTotals: List<RoiTotal> = emptyList(),
    val collapsedSectionIds: Set<String> = emptySet(),
    val showExportDialog: Boolean = false,
    val exportFilename: String = "",
    val copyDialogVisible: Boolean = false,
    val copyPlanName: String = "",
    val confirmDeleteDialogVisible: Boolean = false,
)

class PlanDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataSource: PlanDataSource,
    private val json: Json,
    private val snackBarDispatcher: SnackBarDispatcher,
) : BaseViewModel(savedStateHandle) {

    private val routeDest: PlanDetailDest = savedStateHandle.toRoute()

    val _uiState = MutableStateFlow(PlanDetailUiState())

    val uiState: StateFlow<PlanDetailUiState> = _uiState.asStateFlow()

    init {
        _appUiState.update {
            it.copy(
                fabState = FabUiState(
                    visible = true,
                    text = "Edit".asUiText(),
                    icon = FabUiState.FabIcon.EDIT,
                    onClick = {
                        _navCommandFlow.tryEmit(
                            NavCommand.Navigate(
                                destination = PlanEditDest(
                                    id = routeDest.id
                                )
                            )
                        )
                    }
                )
            )
        }

        viewModelScope.launch {
            dataSource.getAsFlow(routeDest.id).collect { plan ->
                _uiState.update { prev ->
                    prev.copy(plan = plan)
                }

                _appUiState.update {
                    it.copy(
                        overflowOptions = listOf(
                            OverflowActionBarItem(
                                text = "Export to file",
                                onClick = this@PlanDetailViewModel::onClickSaveToFile,
                            ),
                            OverflowActionBarItem(
                                text = "Make a copy",
                                onClick = this@PlanDetailViewModel::onClickCopy,
                            ),
                            OverflowActionBarItem(
                                text = "Delete",
                                onClick = this@PlanDetailViewModel::onClickDelete,
                            )
                        )
                    )
                }

                if(plan != null) {
                    val costTotalMap = buildMap {
                        val totalsPerCategory = plan.costCategories.map { category ->
                            val costsForCategory = category.costs.map { it.getAnnualTotals(plan) }
                            putAll(costsForCategory.associateBy { it.forId })
                            costsForCategory.sumCostTotals(category.id).also {
                                put(category.id, it)
                            }
                        }

                        put(ID_TOTAL, totalsPerCategory.sumCostTotals(ID_TOTAL))
                    }
                    val grandTotals = costTotalMap[ID_TOTAL] ?: throw IllegalStateException("Uh, what?")

                    val roiTotals = plan.interventions.groupBy {
                        it.roiUnit
                    }.map { roiType ->
                        val roiFrom = roiType.value.sumOf { it.roiFrom.toDouble() }.toFloat()
                        val roiTo = roiType.value.sumOf { it.roiTo.toDouble()}.toFloat()
                        RoiTotal(
                            unit = roiType.key,
                            from = roiFrom,
                            to = roiTo,
                            roiFromPerCurrency = roiFrom / grandTotals.totalCostPerStudent.from,
                            roiToPerCurrency = roiTo / grandTotals.totalCostPerStudent.to,
                        )
                    }

                    _uiState.update { prev ->
                        prev.copy(
                            costTotals = costTotalMap,
                            roiTotals = roiTotals
                        )
                    }
                }

                _appUiState.update { prev ->
                    prev.copy(
                        title = plan?.name?.asUiText()
                    )
                }
            }
        }
    }



    fun onDismissExportDialog() {
        _uiState.update { it.copy(showExportDialog = false) }
    }

    fun onExportFilenameChanged(filename: String) {
        _uiState.update { it.copy(exportFilename = filename) }
    }

    fun onClickSaveToFile() {
        _uiState.update {
            it.copy(
                showExportDialog = true,
                exportFilename = "${it.plan?.name}.txt",
            )
        }
    }

    fun onClickCopy() {
        _uiState.update {
            it.copy(
                copyDialogVisible = true,
                copyPlanName = "Copy of ${it.plan?.name ?: ""}",
            )
        }
    }

    fun onDismissCopyDialog() {
        _uiState.update { it.copy(copyDialogVisible = false) }
    }

    fun onCopyPlanNameChanged(name: String) {
        _uiState.update { it.copy(copyPlanName = name) }
    }

    fun onConfirmCopy() {
        onDismissCopyDialog()
        viewModelScope.launch {
            val plan = _uiState.value.plan ?: return@launch
            val newId = Uuid.random().toString()
            dataSource.store(
                plan.copy(
                    id = newId,
                    name = _uiState.value.copyPlanName,
                )
            )

            _navCommandFlow.tryEmit(
                NavCommand.Navigate(
                    destination = PlanDetailDest(id = newId)
                )
            )

            snackBarDispatcher.showSnackBar(Snack("Copied"))
        }
    }

    fun onClickDelete() {
        _uiState.update { it.copy(confirmDeleteDialogVisible = true) }
    }

    fun onDismissDeleteDialog() {
        _uiState.update { it.copy(confirmDeleteDialogVisible = false) }
    }

    fun onClickConfirmDelete() {
        onDismissDeleteDialog()
        val plan = uiState.value.plan ?: return

        dataSource.delete(plan)

        _navCommandFlow.tryEmit(
            NavCommand.Navigate(
                destination = PlanListDest,
                clearBackStack = true
            )
        )

        snackBarDispatcher.showSnackBar(Snack("Plan deleted"))
    }

    fun onClickConfirmSaveToFile() {
        onDismissExportDialog()
        val plan = uiState.value.plan ?: return
        saveTextFile(
            name = uiState.value.exportFilename,
            text = json.encodeToString(Plan.serializer(), plan)
        )

    }

    fun onToggleSectionIdCollapse(id: String) {
        _uiState.update {
            it.copy(
                collapsedSectionIds = if(it.collapsedSectionIds.contains(id)) {
                    it.collapsedSectionIds - id
                }else {
                    it.collapsedSectionIds + id
                }
            )
        }
    }

    companion object {

        const val ID_TOTAL = "total"
    }

}