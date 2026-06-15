package net.mike_dawson.edtechpreflightchecktool

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import net.mike_dawson.edtechpreflightchecktool.app.AppUiState
import net.mike_dawson.edtechpreflightchecktool.app.FabUiState
import net.mike_dawson.edtechpreflightchecktool.app.PreflightCheckHeader
import net.mike_dawson.edtechpreflightchecktool.components.uiTextStringResource
import net.mike_dawson.edtechpreflightchecktool.ui.theme.AppTheme

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

    var selectedItem by remember {
        mutableIntStateOf(0)
    }

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
                //.safeContentPadding()
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold() { innerPadding ->
                Row(
                    modifier = Modifier.fillMaxSize().consumeWindowInsets(innerPadding)
                ) {
                    NavigationRail(
                        modifier = Modifier.padding(innerPadding),
                        header = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 16.dp),
                            ) {
                                Icon(Icons.Default.FlightTakeoff, contentDescription = null)
                                Spacer(Modifier.width(16.dp))
                                Text(
                                    "EdTech Preflight Calculator",
                                    modifier = Modifier.width(96.dp),
                                    maxLines = 3
                                )
                            }

                            if(appUiStateVal.fabState.visible) {
                                ExtendedFloatingActionButton(
                                    modifier = Modifier.testTag("floating_action_button")
                                        .padding(16.dp),
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
                    ) {
                        Destination.entries.forEachIndexed { index, dest ->
                            NavigationRailItem(
                                selected = index == selectedItem,
                                onClick = {
                                    navController.navigate(
                                        dest.route,
                                        navOptions = navOptions {
                                            popUpTo(0) { inclusive = true }
                                        },
                                    )
                                    selectedItem  = index
                                },
                                label = {
                                    Text(dest.label)
                                },
                                icon = {
                                    Icon(dest.icon, null)
                                }
                            )
                        }

                    }

                    Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        PreflightCheckHeader(
                            appUiState = appUiStateVal,
                            navController = navController,
                        )

                        AppNavHost(
                            navController = navController,
                            onSetAppUiState = {
                                appUiStateVal = it
                            },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
}