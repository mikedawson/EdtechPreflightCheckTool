package net.mike_dawson.edtechpreflightchecktool.app

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import net.mike_dawson.edtechpreflightchecktool.components.uiTextStringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreflightCheckHeader(
    appUiState: AppUiState
) {
    TopAppBar(
        title = {
            Text(
                maxLines = 1,
                text = appUiState.title?.let { uiTextStringResource(it) } ?: "Untitled",
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.testTag("app_title"),
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}