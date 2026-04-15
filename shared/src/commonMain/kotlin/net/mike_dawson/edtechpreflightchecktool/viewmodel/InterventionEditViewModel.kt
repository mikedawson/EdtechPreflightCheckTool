package net.mike_dawson.edtechpreflightchecktool.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.mike_dawson.edtechpreflightchecktool.app.ActionBarButtonUiState
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Intervention
import net.mike_dawson.edtechpreflightchecktool.ext.asUiText
import net.mike_dawson.edtechpreflightchecktool.nav.InterventionEditDest
import net.mike_dawson.edtechpreflightchecktool.nav.NavCommand
import net.mike_dawson.edtechpreflightchecktool.nav.NavResult
import net.mike_dawson.edtechpreflightchecktool.nav.NavResultReturner

data class InterventionEditUiState(
    val intervention: Intervention = Intervention()
)

class InterventionEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val navResultReturner: NavResultReturner,
): BaseViewModel(savedStateHandle) {

    private val routeDest: InterventionEditDest = savedStateHandle.toRoute()

    private val _uiState = MutableStateFlow(
        InterventionEditUiState(
            intervention = routeDest.intervention
        )
    )

    val uiState = _uiState.asStateFlow()

    init {
        _appUiState.update {
            it.copy(
                title = "Edit intervention".asUiText(),
                actionBarButtonState = ActionBarButtonUiState(
                    visible = true,
                    text = "Done".asUiText(),
                    enabled = true,
                    onClick = ::onClickDone
                )
            )
        }
    }


    fun onChange(intervention: Intervention) {
        _uiState.update { prev -> prev.copy(intervention = intervention) }
    }


    fun onClickDone() {
        navResultReturner.sendResult(
            NavResult(
                key = RESULT_KEY_INTERVENTION,
                result = uiState.value.intervention
            )
        )
        _navCommandFlow.tryEmit(
            NavCommand.PopUp()
        )
    }

    companion object {

        const val RESULT_KEY_INTERVENTION = "result_intervention"

    }

}