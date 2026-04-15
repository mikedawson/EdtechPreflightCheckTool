package net.mike_dawson.edtechpreflightchecktool.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanDetailUiState
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanDetailViewModel

@Composable
fun PlanDetailScreen(
    viewModel: PlanDetailViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    PlanDetailScreen(
        uiState = uiState
    )
}

@Composable
fun PlanDetailScreen(
    uiState: PlanDetailUiState
) {
    Text(uiState.plan?.id ?: "")
}