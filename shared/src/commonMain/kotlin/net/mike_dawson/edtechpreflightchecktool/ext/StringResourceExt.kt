package net.mike_dawson.edtechpreflightchecktool.ext

import net.mike_dawson.edtechpreflightchecktool.app.StringResourceUiText
import net.mike_dawson.edtechpreflightchecktool.app.UiText
import org.jetbrains.compose.resources.StringResource

fun StringResource.asUiText(): UiText {
    return StringResourceUiText(this)
}
