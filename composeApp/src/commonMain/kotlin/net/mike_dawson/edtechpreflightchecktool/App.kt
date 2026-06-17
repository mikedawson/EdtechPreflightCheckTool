package net.mike_dawson.edtechpreflightchecktool

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.window.core.layout.WindowSizeClass
import kotlinx.coroutines.flow.collectLatest
import net.mike_dawson.edtechpreflightchecktool.app.AppUiState
import net.mike_dawson.edtechpreflightchecktool.app.PreflightCheckHeader
import net.mike_dawson.edtechpreflightchecktool.app.SnackBarFlowDispatcher
import net.mike_dawson.edtechpreflightchecktool.components.PreflightFab
import net.mike_dawson.edtechpreflightchecktool.ui.theme.AppTheme
import org.koin.compose.getKoin

@Composable
@Preview
fun App() {
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val isCompact = !adaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(
        WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND
    )

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

    val snackbarHostState = remember { SnackbarHostState() }

    val koin = getKoin()

    LaunchedEffect(Unit) {
        koin.get<SnackBarFlowDispatcher>().snackFlow.collectLatest {
            snackbarHostState.showSnackbar(it.message, it.action)
        }
    }

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
                //.safeContentPadding()
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(snackbarHostState)
                },
                floatingActionButton = {
                    if(isCompact && appUiStateVal.fabState.visible) {
                        PreflightFab(appUiStateVal)
                    }
                },
                bottomBar = {
                    if(isCompact && !appUiStateVal.hideBottomBar) {
                        NavigationBar {
                            Destination.entries.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(item.icon, contentDescription = null)
                                    },
                                    label = {
                                        Text(item.label, maxLines = 1)
                                    },
                                    selected = selectedItem == index,
                                    onClick = {
                                        navController.navigate(
                                            item.route,
                                            navOptions = navOptions {
                                                popUpTo(0) { inclusive = true }
                                            },
                                        )
                                        selectedItem  = index
                                    }
                                )

                            }
                        }
                    }

                }
            ) { innerPadding ->
                Row(
                    modifier = Modifier.fillMaxSize().consumeWindowInsets(innerPadding)
                ) {
                    if(!isCompact) {
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
                                    PreflightFab(appUiStateVal)
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