package net.mike_dawson.edtechpreflightchecktool.viewmodel

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.mike_dawson.edtechpreflightchecktool.app.FabUiState
import net.mike_dawson.edtechpreflightchecktool.app.StringUiText
import net.mike_dawson.edtechpreflightchecktool.nav.NavCommand
import net.mike_dawson.edtechpreflightchecktool.nav.PlanEditDest

data class PlanListUiState(
    val plans: List<String> = emptyList()
)

class PlanListViewModel(
    val savedStateHandle: SavedStateHandle,
): BaseViewModel() {

    private val _uiState = MutableStateFlow(PlanListUiState())

    val uiState: StateFlow<PlanListUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(plans = listOf("Bad", "Worse", "Terrible")) }

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
                                destination = PlanEditDest(id = "")
                            )
                        )
                    }
                )
            )
        }
    }

}