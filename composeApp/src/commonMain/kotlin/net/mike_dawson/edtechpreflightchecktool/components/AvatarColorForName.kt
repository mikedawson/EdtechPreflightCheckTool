package net.mike_dawson.edtechpreflightchecktool.components

import androidx.compose.ui.graphics.Color


fun Int.rgbaColor(): Color = Color(
    red = (this shr 24) and 0xff,
    green = (this shr 16) and 0xff,
    blue = (this shr 8) and 0xff
)


//As per https://mui.com/material-ui/react-avatar/#letter-avatars
fun avatarColorForName(name: String): Int {
    var hash = 0
    name.forEach {
        hash = it.code + ((hash shl 5) - hash)
    }

    val colorInt = hash or 0xff
    return colorInt

}

