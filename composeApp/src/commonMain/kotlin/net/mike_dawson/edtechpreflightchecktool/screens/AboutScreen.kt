package net.mike_dawson.edtechpreflightchecktool.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.mike_dawson.edtechpreflightchecktool.components.defaultItemPadding
import net.mike_dawson.edtechpreflightchecktool.viewmodel.AboutViewModel

@Composable
fun AboutScreen(
    viewModel: AboutViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth().defaultItemPadding(),
    ) {
        Text("EdTech Preflight Calculator: Developed by Mike Dawson. Funded by and developed  " +
                "based on guidance from EdTech Hub. Copyright 2026 EdTech Hub. \n\n" +
                "This is Open Source Software under the MIT license. \n\n" +
                "Further details to follow here. "
        )
    }
}