package net.mike_dawson.edtechpreflightchecktool.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.russhwolf.settings.Settings
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
import net.mike_dawson.edtechpreflightchecktool.app.StringUiText
import net.mike_dawson.edtechpreflightchecktool.datalayer.datasource.PlanDataSource
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Plan
import net.mike_dawson.edtechpreflightchecktool.nav.NavCommand
import net.mike_dawson.edtechpreflightchecktool.nav.PlanDetailDest
import net.mike_dawson.edtechpreflightchecktool.nav.PlanEditDest

data class PlanListUiState(
    val plans: List<Plan> = emptyList(),
    val showFirstUseDialog: Boolean = false,
    val dontShowFirstUseAgainChecked: Boolean = true,
)

class PlanListViewModel(
    savedStateHandle: SavedStateHandle,
    private val planDataSource: PlanDataSource,
    private val json: Json,
    private val snackBarDispatcher: SnackBarDispatcher,
    private val settings: Settings,
): BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(PlanListUiState())

    val uiState: StateFlow<PlanListUiState> = _uiState.asStateFlow()

    init {
        _appUiState.update {
            it.copy(
                title = StringUiText("Plans"),
                fabState = FabUiState(
                    visible = true,
                    text = StringUiText("Plan"),
                    icon = FabUiState.FabIcon.ADD,
                    onClick = {
                        _navCommandFlow.tryEmit(
                            NavCommand.Navigate(
                                destination = PlanEditDest(id = null)
                            )
                        )
                    }
                ),
                overflowOptions = listOf(
                    OverflowActionBarItem(
                        text = "Import from file",
                        onClick = this@PlanListViewModel::onClickImportFromFile,
                    ),
                ),
                showBackButton = false,
            )
        }

        if(!settings.getBoolean(DONT_SHOW_FIRST_USE_DIALOG, false)) {
            _uiState.update { it.copy(showFirstUseDialog = true) }
        }

        viewModelScope.launch {
            planDataSource.listAllAsFlow().collect {
                _uiState.update { prev ->
                    prev.copy(plans = it)
                }
            }
        }
    }

    fun onClickImportFromFile() {
        openTextFile { text ->
            try {
                val plan: Plan = json.decodeFromString(Plan.serializer(), text)
                viewModelScope.launch {
                    planDataSource.store(plan)
                }
            }catch(_: Throwable) {
                snackBarDispatcher.showSnackBar(Snack("Error: invalid file"))
            }
        }
    }

    fun onClickItem(
        plan: Plan
    ) {
        _navCommandFlow.tryEmit(
            NavCommand.Navigate(
                destination = PlanDetailDest(id = plan.id)
            )
        )
    }


    fun onDismissFirstUseDialog() {
        _uiState.update {
            it.copy(showFirstUseDialog = false)
        }
    }

    fun onClickFirstUseDialogOk() {
        if(uiState.value.dontShowFirstUseAgainChecked) {
            println("setting dont show first use dialog")
            settings.putBoolean(DONT_SHOW_FIRST_USE_DIALOG, true)
        }

        _uiState.update {
            it.copy(showFirstUseDialog = false)
        }
    }

    fun onChangeDontShowFirstUseDialogAgain(checked: Boolean) {
        _uiState.update { it.copy(dontShowFirstUseAgainChecked = checked) }
    }

    companion object {

        /**
         * True when the first use dialog has been shown wiht the
         */
        const val DONT_SHOW_FIRST_USE_DIALOG = "first_use_dialog_shown"

    }

}