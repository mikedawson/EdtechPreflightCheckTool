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
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Plan
import net.mike_dawson.edtechpreflightchecktool.ext.asUiText
import net.mike_dawson.edtechpreflightchecktool.nav.NavCommand
import net.mike_dawson.edtechpreflightchecktool.nav.PlanDetailDest
import net.mike_dawson.edtechpreflightchecktool.nav.PlanEditDest

data class PlanDetailUiState(
    val plan: Plan? = null,
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
            val plan = dataSource.get(routeDest.id)
            _uiState.update { prev ->
                prev.copy(
                    plan = plan
                )
            }

            _appUiState.update { prev ->
                prev.copy(
                    title = plan?.name?.asUiText()
                )
            }
        }
    }

}