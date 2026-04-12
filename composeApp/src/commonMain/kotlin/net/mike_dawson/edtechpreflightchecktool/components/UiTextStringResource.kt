package net.mike_dawson.edtechpreflightchecktool.components

import androidx.compose.runtime.Composable
import net.mike_dawson.edtechpreflightchecktool.app.StringResourceUiText
import net.mike_dawson.edtechpreflightchecktool.app.StringUiText
import net.mike_dawson.edtechpreflightchecktool.app.UiText
import org.jetbrains.compose.resources.stringResource

@Composable
fun uiTextStringResource(uiText: UiText): String {
    return when(uiText) {
        is StringResourceUiText -> {
            stringResource(uiText.resource)
        }
        is StringUiText -> uiText.text
    }
}
