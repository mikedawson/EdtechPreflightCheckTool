package net.mike_dawson.edtechpreflightchecktool.viewmodel

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.mike_dawson.edtechpreflightchecktool.datalayer.Plan
import net.mike_dawson.edtechpreflightchecktool.ext.asUiText

data class PlanEditUiState(
    val plan: Plan = Plan()
)

class PlanEditViewModel(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(PlanEditUiState())

    val uiState: StateFlow<PlanEditUiState> = _uiState.asStateFlow()

    init {
        _appUiState.update {
            it.copy(
                title = "Edit Plan".asUiText()
            )
        }
    }

    fun onPlanChange(
        plan: Plan
    ) {
        _uiState.update { prev ->
            prev.copy(plan = plan)
        }
    }

}