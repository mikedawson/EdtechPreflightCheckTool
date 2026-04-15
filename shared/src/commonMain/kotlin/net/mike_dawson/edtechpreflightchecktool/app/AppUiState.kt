package net.mike_dawson.edtechpreflightchecktool.app

data class AppUiState(
    val title: UiText? = null,
    val fabState: FabUiState = FabUiState(),
    val showBackButton: Boolean? = null,
    val actionBarButtonState: ActionBarButtonUiState = ActionBarButtonUiState(),
)
