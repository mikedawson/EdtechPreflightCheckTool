package net.mike_dawson.edtechpreflightchecktool.app

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import net.mike_dawson.edtechpreflightchecktool.components.uiTextStringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreflightCheckHeader(
    appUiState: AppUiState,
    navController: NavController,
) {

    val currentBackStack by navController.currentBackStack.collectAsState()

    val canGoBack = appUiState.showBackButton ?: (currentBackStack.size > 1)

    var showOverflowMenu: Boolean by remember {
        mutableStateOf(false)
    }

    TopAppBar(
        title = {
            Text(
                maxLines = 1,
                text = appUiState.title?.let { uiTextStringResource(it) } ?: "Untitled",
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.testTag("app_title"),
            )
        },
        navigationIcon = {
            if(canGoBack) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            if(appUiState.actionBarButtonState.visible) {
                Button(
                    onClick = appUiState.actionBarButtonState.onClick,
                    enabled = appUiState.actionBarButtonState.enabled ?: true,
                    modifier = Modifier.testTag("action_bar_button"),
                ) {
                    Text(
                        text = appUiState.actionBarButtonState.text?.let {
                            uiTextStringResource(it)
                        } ?: ""
                    )
                }
            }

            if(appUiState.overflowOptions.isNotEmpty()) {
                Box {
                    IconButton(
                        onClick = { showOverflowMenu = true },
                        modifier = Modifier.testTag("more_options"),
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "More options"
                        )
                    }

                    DropdownMenu(
                        expanded = showOverflowMenu,
                        onDismissRequest = { showOverflowMenu = false }
                    ) {
                        appUiState.overflowOptions.forEach { action ->
                            DropdownMenuItem(
                                text = { Text(action.text) },
                                onClick = {
                                    action.onClick()
                                    showOverflowMenu = false
                                }
                            )
                        }
                    }
                }
            }
        },
    )
}