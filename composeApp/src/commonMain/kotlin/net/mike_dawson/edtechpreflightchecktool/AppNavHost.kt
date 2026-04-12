package net.mike_dawson.edtechpreflightchecktool

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.mike_dawson.edtechpreflightchecktool.app.AppUiState
import net.mike_dawson.edtechpreflightchecktool.app.PlanListDest
import net.mike_dawson.edtechpreflightchecktool.components.preflightViewModel
import net.mike_dawson.edtechpreflightchecktool.screens.PlanListScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    onSetAppUiState: (AppUiState) -> Unit,
    modifier: Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = PlanListDest,
        modifier = modifier,
    ) {

        composable<PlanListDest> {
            PlanListScreen(
                viewModel = preflightViewModel(
                    onSetAppUiState = onSetAppUiState
                )
            )
        }
    }
}