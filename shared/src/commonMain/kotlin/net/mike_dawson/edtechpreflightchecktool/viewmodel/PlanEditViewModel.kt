package net.mike_dawson.edtechpreflightchecktool.viewmodel

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.update
import net.mike_dawson.edtechpreflightchecktool.ext.asUiText


class PlanEditViewModel(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    init {
        _appUiState.update {
            it.copy(
                title = "Edit Plan".asUiText()
            )
        }
    }

}