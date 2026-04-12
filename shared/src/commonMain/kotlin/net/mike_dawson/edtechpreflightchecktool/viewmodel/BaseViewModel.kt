package net.mike_dawson.edtechpreflightchecktool.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.mike_dawson.edtechpreflightchecktool.app.AppUiState

abstract class BaseViewModel: ViewModel() {
    protected val _appUiState = MutableStateFlow(AppUiState())

    val appUiState: StateFlow<AppUiState> = _appUiState.asStateFlow()

}