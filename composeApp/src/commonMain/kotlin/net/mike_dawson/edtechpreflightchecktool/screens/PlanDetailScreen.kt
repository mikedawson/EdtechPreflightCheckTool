package net.mike_dawson.edtechpreflightchecktool.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import kotlinx.coroutines.Dispatchers
import net.mike_dawson.edtechpreflightchecktool.components.CostTotalsColumn
import net.mike_dawson.edtechpreflightchecktool.components.IconColorCircle
import net.mike_dawson.edtechpreflightchecktool.components.InfoCard
import net.mike_dawson.edtechpreflightchecktool.components.defaultItemPadding
import net.mike_dawson.edtechpreflightchecktool.components.formatCost
import net.mike_dawson.edtechpreflightchecktool.components.toDisplayString
import net.mike_dawson.edtechpreflightchecktool.components.toFromPercentStr
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanDetailUiState
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanDetailScreen(
    viewModel: PlanDetailViewModel,
) {
    val uiState by viewModel.uiState.collectAsState(Dispatchers.Main.immediate)

    PlanDetailScreen(
        uiState = uiState,
        onToggleSectionIdCollapse = viewModel::onToggleSectionIdCollapse,
    )

    if(uiState.showExportDialog) {
        //As per https://kotlinlang.org/api/compose-multiplatform/material3/androidx.compose.material3/-basic-alert-dialog.html
        FilenameAlertDialog(
            onDismiss = viewModel::onDismissExportDialog,
            filename = uiState.exportFilename,
            onFilenameChanged = viewModel::onExportFilenameChanged,
            textFieldLabel = {
                Text("Filename")
            },
            confirmButton = {
                Button(
                    onClick = viewModel::onClickConfirmSaveToFile
                ) {
                    Text("Save")
                }
            },
            infoContent = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.Info, contentDescription = null)
                    Spacer(Modifier.width(16.dp))
                    Text("After you download the text file to your device, you can import it on any other device by clicking the Planner tab and selecting the Import from file option from three dots in the top right corner")
                }

                Spacer(Modifier.height(24.dp))
            }
        )
    }

    if(uiState.copyDialogVisible) {
        FilenameAlertDialog(
            onDismiss = viewModel::onDismissCopyDialog,
            filename = uiState.copyPlanName,
            onFilenameChanged = viewModel::onCopyPlanNameChanged,
            textFieldLabel = {
                Text("Name*")
            },
            confirmButton = {
                Button(
                    onClick = viewModel::onConfirmCopy
                ) {
                    Text("Make a copy")
                }
            },
        )
    }

    if(uiState.confirmDeleteDialogVisible) {
        BasicAlertDialog(
            onDismissRequest = viewModel::onDismissDeleteDialog,
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
                    Text("Are you sure you want to delete this plan? This is permanent.")

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                viewModel.onDismissDeleteDialog()
                            }
                        ) {
                            Text("Cancel")
                        }

                        Spacer(Modifier.width(16.dp))

                        Button(
                            onClick = {
                                viewModel.onClickConfirmDelete()
                            }
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PlanDetailScreen(
    uiState: PlanDetailUiState,
    onToggleSectionIdCollapse: (String) -> Unit = { },
) {
    val currencySymbol = uiState.plan?.currency?.symbol?: ""
    val currencyCode = uiState.plan?.currency?.code ?: ""
    val plan = uiState.plan

    val isExpandedLayout = currentWindowAdaptiveInfo().windowSizeClass.isWidthAtLeastBreakpoint(
        WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND
    )

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        if(plan != null) {
            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    InfoCard(
                        headlineText = "Students",
                        contentText = plan.targetNumStudents.toString(),
                    )

                    InfoCard(
                        headlineText = "Avg. students per class",
                        contentText = plan.averageStudentsPerClass.toDisplayString(),
                    )

                    InfoCard(
                        headlineText = "Avg classes per school",
                        contentText = plan.averageClassesPerSchool.toDisplayString(),
                    )
                }
            }

            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    uiState.costTotals[PlanDetailViewModel.ID_TOTAL]?.also { grandTotals ->
                        InfoCard(
                            headlineText = "Total cost/year",
                            contentText = grandTotals.totalCost.toDisplayString(
                                currencySymbol = currencySymbol
                            )
                        )

                        InfoCard(
                            headlineText = "Marginal cost/student/year",
                            contentText = grandTotals.totalMarginalCostPerStudent.toDisplayString(
                                currencySymbol = currencySymbol
                            )
                        )

                        InfoCard(
                            headlineText = "Total cost/student/year",
                            contentText = grandTotals.totalCostPerStudent.toDisplayString(
                                currencySymbol = currencySymbol
                            )
                        )
                    }
                }
            }

            uiState.roiTotals.takeIf { it.isNotEmpty() }?.also { roiTotals ->
                item {
                    Box(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Card(
                            colors = CardDefaults.outlinedCardColors(),
                            border = BorderStroke(1.dp, Color.Black),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(16.dp).width(512.dp),
                            ) {
                                Text("Expected Return on Investment Ranges")
                                roiTotals.forEach { roiTotal ->
                                    Text(
                                        style = MaterialTheme.typography.headlineLarge,
                                        text = "${roiTotal.roiFromPer100Currency.toDisplayString()}-${roiTotal.roiToPer100Currency.toDisplayString()} per 100 $currencySymbol $currencyCode",
                                    )
                                    Text(roiTotal.unit.displayName)
                                    HorizontalDivider()
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        }
                    }
                }
            }

            item {
                ListItem(
                    headlineContent = {
                        Text("Interventions (${plan.interventions.size})")
                    }
                )
            }

            plan.interventions.forEach { intervention ->
                item {
                    ListItem(
                        leadingContent = {
                            IconColorCircle(name = intervention.name) {
                                Icon(Icons.AutoMirrored.Filled.ListAlt, contentDescription = null)
                            }
                        },
                        headlineContent = {
                            Text(intervention.name)
                        },
                        supportingContent = {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Text("Return on investment: ${intervention.roiUnit.displayName}: ${intervention.roiFrom} to ${intervention.roiTo}")
                                Text("License: ${intervention.licenseType.displayName}")
                            }
                        },
                    )
                }
            }


            plan.costCategories.forEach { category ->
                val isCollapsed = category.id in uiState.collapsedSectionIds

                item {
                    val totals = uiState.costTotals[category.id]

                    ListItem(
                        modifier = Modifier.clickable {
                            onToggleSectionIdCollapse(category.id)
                        },
                        leadingContent = {
                            IconColorCircle(
                                name = category.name
                            ) {
                                Icon(Icons.AutoMirrored.Outlined.Label, null)
                            }
                        },
                        headlineContent = { Text(category.name) },
                        supportingContent = {
                            Column {
                                if(!isExpandedLayout) {
                                    totals?.also { _ ->
                                        CostTotalsColumn(
                                            currencySymbol = currencySymbol,
                                            totals = totals
                                        )

                                        Spacer(Modifier.height(8.dp))
                                    }
                                }

                                totals?.percentageOfTotalTo?.also {
                                    Text("${toFromPercentStr(totals.percentageOfTotalFrom, totals.percentageOfTotalTo)} total cost, " +
                                            "${toFromPercentStr(totals.percentageOfTotalMarginalFrom, totals.percentageOfTotalMarginalTo)} marginal cost per student")
                                }
                            }

                        },
                        trailingContent = {
                            Row {
                                if(isExpandedLayout) {
                                    uiState.costTotals[category.id]?.also { totals ->
                                        CostTotalsColumn(
                                            currencySymbol = currencySymbol,
                                            totals = totals,
                                            textAlign = TextAlign.End,
                                            horizontalAlignment = Alignment.End,
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = { onToggleSectionIdCollapse(category.id) }
                                ) {
                                    Icon(
                                        imageVector = if(isCollapsed) {
                                            Icons.Default.ExpandMore
                                        }else{
                                            Icons.Default.ExpandLess
                                        },
                                        contentDescription = if(isCollapsed) {
                                            "Expand"
                                        }else {
                                            "Collapse"
                                        }
                                    )
                                }
                            }

                        }
                    )
                }

                if(!isCollapsed) {
                    category.costs.forEach { cost ->
                        val totals = uiState.costTotals[cost.id]

                        item {
                            ListItem(
                                leadingContent = {
                                    IconColorCircle(
                                        name = cost.name
                                    ) {
                                        Icon(
                                            Icons.Default.Receipt,
                                            contentDescription = null,
                                        )
                                    }
                                },
                                headlineContent = { Text(cost.name) },
                                supportingContent = {
                                    Text(formatCost(cost, plan.currency))

                                    if(!isExpandedLayout && totals != null) {
                                        CostTotalsColumn(
                                            currencySymbol = currencySymbol,
                                            totals = totals,
                                        )
                                    }
                                },
                                trailingContent = {
                                    Row {
                                        if(isExpandedLayout && totals != null) {
                                            CostTotalsColumn(
                                                currencySymbol = currencySymbol,
                                                totals = totals,
                                                textAlign = TextAlign.End,
                                                horizontalAlignment = Alignment.End,
                                            )
                                        }

                                        Spacer(Modifier.size(48.dp))
                                    }
                                }
                            )
                        }
                    }

                    if(category.costs.isEmpty()) {
                        item {
                            Text(
                                "[No costs in this category]",
                                modifier = Modifier.fillMaxWidth().defaultItemPadding(),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }else {
            //show loading
        }

        item {
            Spacer(Modifier.height(96.dp))
        }
    }
}