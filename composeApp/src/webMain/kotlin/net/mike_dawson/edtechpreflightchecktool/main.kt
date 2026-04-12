package net.mike_dawson.edtechpreflightchecktool

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import net.mike_dawson.edtechpreflightchecktool.di.wasmKoinModule
import org.koin.compose.KoinApplication

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        KoinApplication(
            application = {
                modules(wasmKoinModule)
            }
        ) {
            App()
        }
    }
}
