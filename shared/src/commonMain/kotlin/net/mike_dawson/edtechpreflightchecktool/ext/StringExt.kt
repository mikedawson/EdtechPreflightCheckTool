package net.mike_dawson.edtechpreflightchecktool.ext

import net.mike_dawson.edtechpreflightchecktool.app.StringUiText
import net.mike_dawson.edtechpreflightchecktool.app.UiText

fun String.asUiText(): UiText {
    return StringUiText(this)
}
