package net.mike_dawson.edtechpreflightchecktool.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.mike_dawson.edtechpreflightchecktool.app.FabUiState
import net.mike_dawson.edtechpreflightchecktool.app.StringUiText
import net.mike_dawson.edtechpreflightchecktool.datalayer.datasource.PlanDataSource
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Plan
import net.mike_dawson.edtechpreflightchecktool.nav.NavCommand
import net.mike_dawson.edtechpreflightchecktool.nav.PlanDetailDest
import net.mike_dawson.edtechpreflightchecktool.nav.PlanEditDest

data class PlanListUiState(
    val plans: List<Plan> = emptyList()
)

class PlanListViewModel(
    savedStateHandle: SavedStateHandle,
    private val planDataSource: PlanDataSource,
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
                showBackButton = false,
            )
        }

        viewModelScope.launch {
            planDataSource.listAllAsFlow().collect {
                _uiState.update { prev ->
                    prev.copy(plans = it)
                }
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

}