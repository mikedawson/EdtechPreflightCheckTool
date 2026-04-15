package net.mike_dawson.edtechpreflightchecktool.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Plan
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanListUiState
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanListViewModel

@Composable
fun PlanListScreen(
    viewModel: PlanListViewModel,
) {

    val uiState by viewModel.uiState.collectAsState()

    PlanListScreen(
        uiState = uiState,
        onCLickItem = viewModel::onClickItem,
    )
}

@Composable
fun PlanListScreen(
    uiState: PlanListUiState,
    onCLickItem: (Plan) -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        uiState.plans.forEach { plan ->
            ListItem(
                modifier = Modifier.clickable{
                    onCLickItem(plan)
                },
                headlineContent = {
                    Text(plan.name)
                }
            )
        }
    }

}
