package net.mike_dawson.edtechpreflightchecktool.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.mike_dawson.edtechpreflightchecktool.components.PreflightExposedDropDownMenuField
import net.mike_dawson.edtechpreflightchecktool.components.UstadNumberTextField
import net.mike_dawson.edtechpreflightchecktool.components.defaultItemPadding
import net.mike_dawson.edtechpreflightchecktool.components.formatCost
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Cost
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostCategory
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Intervention
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Plan
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanEditUiState
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanEditViewModel

@Composable
fun PlanEditScreen(
    viewModel: PlanEditViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    PlanEditScreen(
        uiState = uiState,
        onPlanChange = viewModel::onPlanChange,
        onClickNewCost = viewModel::onClickNewCost,
        onClickCost = viewModel::onClickCost,
        onClickDeleteCost = viewModel::onClickDeleteCost,
        onClickAddIntervention = viewModel::onClickAddIntervention,
        onClickIntervention = viewModel::onClickIntervention,
        onClickDeleteIntervention = viewModel::onClickDeleteIntervention,
    )
}

@Composable
fun PlanEditScreen(
    uiState: PlanEditUiState,
    onPlanChange: (Plan) -> Unit = { },
    onClickNewCost: (CostCategory) -> Unit = { },
    onClickCost: (Cost) -> Unit = { },
    onClickDeleteCost: (Cost) -> Unit = { },
    onClickAddIntervention: () -> Unit = { },
    onClickIntervention: (Intervention) -> Unit = { },
    onClickDeleteIntervention: (Intervention) -> Unit = { },
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().defaultItemPadding(),
            value = uiState.plan.name,
            singleLine = true,
            label = { Text("Name") },
            onValueChange = {
                onPlanChange(uiState.plan.copy(name = it))
            }
        )

        PreflightExposedDropDownMenuField(
            modifier = Modifier.fillMaxWidth().defaultItemPadding(),
            value = uiState.plan.currency,
            label = { Text("Currency") },
            options = uiState.currencyOptions,
            onOptionSelected = {
                onPlanChange(uiState.plan.copy(currency = it))
            },
            itemLabel = {
                "${it.code} - ${it.name}"
            },
        )

        UstadNumberTextField(
            value = uiState.plan.averageStudentsPerClass,
            modifier = Modifier.defaultItemPadding().fillMaxWidth(),
            label = { Text("Average students per class") },
            onValueChange = {
                onPlanChange(uiState.plan.copy(averageStudentsPerClass = it))
            }
        )

        UstadNumberTextField(
            value = uiState.plan.averageClassesPerSchool,
            modifier = Modifier.defaultItemPadding().fillMaxWidth(),
            label = { Text("Average classes per school") },
            onValueChange = {
                onPlanChange(uiState.plan.copy(averageClassesPerSchool = it))
            }
        )

        UstadNumberTextField(
            value = uiState.plan.targetNumStudents.toFloat(),
            modifier = Modifier.defaultItemPadding().fillMaxWidth(),
            label = { Text("Target number of students") },
            onValueChange = {
                onPlanChange(uiState.plan.copy(targetNumStudents = it.toInt()))
            }
        )

        ListItem(
            modifier = Modifier.clickable {
                onClickAddIntervention()
            },
            leadingContent = {
                Icon(Icons.Default.Add, contentDescription = null)
            },
            headlineContent = {
                Text("Add intervention")
            }
        )

        uiState.plan.interventions.forEach { intervention ->
            ListItem(
                modifier = Modifier.clickable {
                    onClickIntervention(intervention)
                },
                leadingContent = {
                    Icon(Icons.AutoMirrored.Filled.ListAlt, contentDescription = null)
                },
                headlineContent = {
                    Text(intervention.name + " (${intervention.category.displayName})")
                },
                supportingContent = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text("LAYS: ${intervention.laysFrom} to ${intervention.laysTo}")
                        Text("License: ${intervention.licenseType.displayName}")
                    }
                },
                trailingContent = {
                    IconButton(
                        onClick = {
                            onClickDeleteIntervention(intervention)
                        }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )

        }

        uiState.plan.costCategories.forEach { costCategory ->
            ListItem(
                headlineContent = {
                    Text(costCategory.name)
                }
            )

            ListItem(
                modifier = Modifier.clickable {
                    onClickNewCost(costCategory)
                },
                leadingContent = {
                    Icon(Icons.Default.Add, contentDescription = null)
                },
                headlineContent = {
                    Text("Add ${costCategory.name} cost")
                }
            )

            costCategory.costs.forEach { cost ->
                ListItem(
                    modifier = Modifier.clickable {
                        onClickCost(cost)
                    },
                    leadingContent = {
                        Icon(
                            Icons.Default.Receipt,
                            contentDescription = null,
                        )
                    },
                    headlineContent = {
                        Text(cost.name)
                    },
                    supportingContent = {
                        Text(formatCost(cost, uiState.plan.currency))
                    },
                    trailingContent = {
                        IconButton(
                            onClick = {onClickDeleteCost(cost)}
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                )
            }

        }

    }
}


@Preview
@Composable
fun PlanEditScreenPreview() {
    PlanEditScreen(PlanEditUiState())
}
