package net.mike_dawson.edtechpreflightchecktool.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.mike_dawson.edtechpreflightchecktool.app.FabUiState
import net.mike_dawson.edtechpreflightchecktool.datalayer.datasource.PlanDataSource
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostTotals
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.LaysTotal
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Plan
import net.mike_dawson.edtechpreflightchecktool.ext.asUiText
import net.mike_dawson.edtechpreflightchecktool.ext.getAnnualTotals
import net.mike_dawson.edtechpreflightchecktool.ext.sumCostTotals
import net.mike_dawson.edtechpreflightchecktool.nav.NavCommand
import net.mike_dawson.edtechpreflightchecktool.nav.PlanDetailDest
import net.mike_dawson.edtechpreflightchecktool.nav.PlanEditDest

data class PlanDetailUiState(
    val plan: Plan? = null,
    val costTotals: Map<String, CostTotals> = emptyMap(),
    val laysTotal: LaysTotal? = null,
    val collapsedSectionIds: Set<String> = emptySet(),
)

class PlanDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataSource: PlanDataSource,
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

                    val laysFromTotal = plan.interventions.sumOf { it.laysFrom.toDouble() }.toFloat()
                    val laysToTotal = plan.interventions.sumOf { it.laysTo.toDouble() }.toFloat()

                    _uiState.update { prev ->
                        prev.copy(
                            costTotals = costTotalMap,
                            laysTotal = if(laysFromTotal != 0f && laysToTotal != 0f) {
                                LaysTotal(
                                    from = laysFromTotal,
                                    to = laysToTotal,
                                    laysFromPerCurrency = laysFromTotal / grandTotals.totalCostPerStudent,
                                    laysToPerCurrency = laysToTotal / grandTotals.totalCostPerStudent
                                )
                            }else {
                                null
                            }
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