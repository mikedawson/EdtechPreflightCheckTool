package net.mike_dawson.edtechpreflightchecktool.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.mike_dawson.edtechpreflightchecktool.components.IconColorCircle
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Plan
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanListUiState
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanListScreen(
    viewModel: PlanListViewModel,
) {

    val uiState by viewModel.uiState.collectAsState()

    if(uiState.showFirstUseDialog) {
        BasicAlertDialog(
            onDismissRequest = viewModel::onDismissFirstUseDialog,
        ) {
            Surface(
                modifier = Modifier.widthIn(max = 300.dp).wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Welcome to the EdTech pre-flight check tool! This is a quick cost estimator that can help you make decisions about how to optimise the cost-efficiency of an EdTech intervention you are planning, scaling, or already undertaking. This tool allows you to enter different categories of costs and other parameters to help you determine what impact your programme can have on learning outcomes.")

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = uiState.dontShowFirstUseAgainChecked,
                            onCheckedChange = { checked ->
                                viewModel.onChangeDontShowFirstUseDialogAgain(checked)
                            }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Don't show this again")
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                viewModel.onClickFirstUseDialogOk()
                            }
                        ) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }

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
                leadingContent = {
                    IconColorCircle(
                        name = plan.name
                    ) {
                        Icon(Icons.Outlined.Map, contentDescription = null)
                    }
                },
                headlineContent = {
                    Text(plan.name)
                }
            )
        }
    }

}
