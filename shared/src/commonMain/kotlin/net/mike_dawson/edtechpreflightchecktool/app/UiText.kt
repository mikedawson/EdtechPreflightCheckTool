package net.mike_dawson.edtechpreflightchecktool.app

import org.jetbrains.compose.resources.StringResource

sealed class UiText

data class StringResourceUiText(val resource: StringResource): UiText()

data class StringUiText(val text: String): UiText()


