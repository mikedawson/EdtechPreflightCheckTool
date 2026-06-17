package net.mike_dawson.edtechpreflightchecktool.viewmodel

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.update
import net.mike_dawson.edtechpreflightchecktool.ext.asUiText

class AboutViewModel(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel(savedStateHandle){

    init {
        _appUiState.update {
            it.copy(
                title = "About".asUiText(),
                showBackButton = false,
            )
        }
    }
}