package net.mike_dawson.edtechpreflightchecktool

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.mike_dawson.edtechpreflightchecktool.app.AppUiState
import net.mike_dawson.edtechpreflightchecktool.nav.PlanListDest
import net.mike_dawson.edtechpreflightchecktool.components.preflightViewModel
import net.mike_dawson.edtechpreflightchecktool.nav.CostEditDest
import net.mike_dawson.edtechpreflightchecktool.nav.PlanDetailDest
import net.mike_dawson.edtechpreflightchecktool.nav.PlanEditDest
import net.mike_dawson.edtechpreflightchecktool.nav.RespectComposeNavController
import net.mike_dawson.edtechpreflightchecktool.screens.CostEditScreen
import net.mike_dawson.edtechpreflightchecktool.screens.PlanDetailScreen
import net.mike_dawson.edtechpreflightchecktool.screens.PlanEditScreen
import net.mike_dawson.edtechpreflightchecktool.screens.PlanListScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    onSetAppUiState: (AppUiState) -> Unit,
    respectNavController: RespectComposeNavController = remember(Unit) {
        RespectComposeNavController(navController)
    },
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
                    onSetAppUiState = onSetAppUiState,
                    navHostController = respectNavController,
                )
            )
        }

        composable<PlanEditDest> {
            PlanEditScreen(
                viewModel = preflightViewModel(
                    onSetAppUiState = onSetAppUiState,
                    navHostController = respectNavController,
                )
            )
        }

        composable<PlanDetailDest> {
            PlanDetailScreen(
                viewModel = preflightViewModel(
                    onSetAppUiState = onSetAppUiState,
                    navHostController = respectNavController,
                )
            )
        }

        composable<CostEditDest> {
            CostEditScreen(
                viewModel = preflightViewModel(
                    onSetAppUiState = onSetAppUiState,
                    navHostController = respectNavController,
                )
            )
        }

    }
}