package net.mike_dawson.edtechpreflightchecktool

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.mike_dawson.edtechpreflightchecktool.app.PlanListDest

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = PlanListDest,
        modifier = modifier,
    ) {
        composable<PlanListDest> {
            Text("Plan list here gov")
        }
    }
}