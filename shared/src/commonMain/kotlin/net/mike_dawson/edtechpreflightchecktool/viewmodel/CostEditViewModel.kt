package net.mike_dawson.edtechpreflightchecktool.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.mike_dawson.edtechpreflightchecktool.app.ActionBarButtonUiState
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Cost
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostRecurrencePeriodEnum
import net.mike_dawson.edtechpreflightchecktool.ext.asUiText
import net.mike_dawson.edtechpreflightchecktool.nav.CostEditDest
import net.mike_dawson.edtechpreflightchecktool.nav.NavCommand
import net.mike_dawson.edtechpreflightchecktool.nav.NavResult
import net.mike_dawson.edtechpreflightchecktool.nav.NavResultReturner

data class CostEditUiState(
    val cost: Cost? = null,
    val durationOptions: List<CostRecurrencePeriodEnum> = CostRecurrencePeriodEnum.entries.toList(),
)


class CostEditViewModel(
    savedState: SavedStateHandle,
    private val navResultReturner: NavResultReturner,
): BaseViewModel(savedState) {

    private val routeDest: CostEditDest = savedState.toRoute()

    private val _uiState = MutableStateFlow(
        CostEditUiState(
            cost = routeDest.cost
        )
    )

    val uiState = _uiState.asStateFlow()

    init {
        _appUiState.update { prev ->
            prev.copy(
                title = "Edit cost".asUiText(),
                actionBarButtonState = ActionBarButtonUiState(
                    visible = true,
                    text = "Done".asUiText(),
                    enabled = true,
                    onClick = ::onClickDone
                )
            )
        }
    }

    fun onChange(cost: Cost) {
        _uiState.update { prev -> prev.copy(cost = cost) }
    }

    fun onClickDone() {
        navResultReturner.sendResult(
            NavResult(
                key = RESULT_KEY_COST,
                result = uiState.value.cost
            )
        )
        _navCommandFlow.tryEmit(NavCommand.PopUp())
    }

    companion object {

        const val RESULT_KEY_COST = "cost"

    }

}