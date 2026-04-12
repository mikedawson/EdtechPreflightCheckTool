package net.mike_dawson.edtechpreflightchecktool.components

import androidx.compose.runtime.Composable
import net.mike_dawson.edtechpreflightchecktool.app.AppUiState
import net.mike_dawson.edtechpreflightchecktool.viewmodel.BaseViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
inline fun <reified T : BaseViewModel> preflightViewModel(
    noinline onSetAppUiState: (AppUiState) -> Unit,
): T {

    val viewModel: T = koinViewModel()

    AppUiStateEffect(
        appUiStateFlow = viewModel.appUiState,
        onSetAppUiState = onSetAppUiState,
    )

    return viewModel
}
