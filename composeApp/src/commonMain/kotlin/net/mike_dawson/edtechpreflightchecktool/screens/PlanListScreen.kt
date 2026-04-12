package net.mike_dawson.edtechpreflightchecktool.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanListUiState
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanListViewModel

@Composable
fun PlanListScreen(
    viewModel: PlanListViewModel
) {

    val uiState by viewModel.uiState.collectAsState()

    PlanListScreen(uiState)
}

@Composable
fun PlanListScreen(
    uiState: PlanListUiState
) {
    Column {
        uiState.plans.forEach {
            Text(it)
        }
    }

}
