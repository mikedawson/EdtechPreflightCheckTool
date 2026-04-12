package net.mike_dawson.edtechpreflightchecktool

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import net.mike_dawson.edtechpreflightchecktool.app.AppUiState
import net.mike_dawson.edtechpreflightchecktool.app.FabUiState
import net.mike_dawson.edtechpreflightchecktool.app.PreflightCheckHeader
import net.mike_dawson.edtechpreflightchecktool.components.uiTextStringResource

@Composable
@Preview
fun App() {
    val appUiState = remember {
        mutableStateOf(
            AppUiState()
        )
    }

    var appUiStateVal by appUiState

    val navController = rememberNavController()

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
                //.safeContentPadding()
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold(
                topBar = {
                    PreflightCheckHeader(
                        appUiState = appUiStateVal,
                        navController = navController,
                    )
                },
                floatingActionButton = {
                    if(appUiStateVal.fabState.visible) {
                        ExtendedFloatingActionButton(
                            modifier = Modifier.testTag("floating_action_button"),
                            onClick = appUiStateVal.fabState.onClick,
                            text = {
                                Text(
                                    modifier = Modifier.testTag("floating_action_button_text"),
                                    text = appUiStateVal.fabState.text?.let {
                                        uiTextStringResource(it)
                                    } ?: ""
                                )
                            },
                            icon = {
                                val imageVector = when (appUiStateVal.fabState.icon) {
                                    FabUiState.FabIcon.ADD -> Icons.Default.Add
                                    FabUiState.FabIcon.EDIT -> Icons.Default.Edit
                                    else -> null
                                }
                                if (imageVector != null) {
                                    Icon(
                                        imageVector = imageVector,
                                        contentDescription = null,
                                    )
                                }
                            }
                        )
                    }

                }
            ) { innerPadding ->
                AppNavHost(
                    navController = navController,
                    onSetAppUiState = {
                        appUiStateVal = it
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}