package net.mike_dawson.edtechpreflightchecktool.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor

@Composable
fun IconColorCircle(
    name: String,
    modifier: Modifier = Modifier.defaultAvatarSize(),
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val bgColor = avatarColorForName(name).rgbaColor()

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(SolidColor(bgColor))
        }

        CompositionLocalProvider(LocalContentColor provides Color.White) {
            content()
        }

    }
}