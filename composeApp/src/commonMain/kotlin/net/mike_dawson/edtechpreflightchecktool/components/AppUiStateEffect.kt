package net.mike_dawson.edtechpreflightchecktool.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.mike_dawson.edtechpreflightchecktool.app.AppUiState

@Composable
fun AppUiStateEffect(
    appUiStateFlow: Flow<AppUiState>,
    onSetAppUiState: (AppUiState) -> Unit,
) {
    LaunchedEffect(appUiStateFlow) {
        //Needs to use .immediate to ensure synchronous update of text state on Search
        withContext(Dispatchers.Main.immediate) {
            appUiStateFlow.collect {
                onSetAppUiState(it)
            }
        }
    }
}
