package net.mike_dawson.edtechpreflightchecktool.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.mike_dawson.edtechpreflightchecktool.components.CostTotalsColumn
import net.mike_dawson.edtechpreflightchecktool.components.InfoCard
import net.mike_dawson.edtechpreflightchecktool.components.formatCost
import net.mike_dawson.edtechpreflightchecktool.components.toDisplayString
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanDetailUiState
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanDetailViewModel

@Composable
fun PlanDetailScreen(
    viewModel: PlanDetailViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    PlanDetailScreen(
        uiState = uiState,
        onToggleSectionIdCollapse = viewModel::onToggleSectionIdCollapse,
    )
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
                            contentText = "$currencySymbol ${grandTotals.totalCost.toDisplayString()}"
                        )

                        InfoCard(
                            headlineText = "Marginal cost/student/year",
                            contentText = "$currencySymbol ${grandTotals.totalMarginalCostPerStudent.toDisplayString()}"
                        )

                        InfoCard(
                            headlineText = "Total cost/student/year",
                            contentText = "$currencySymbol ${grandTotals.totalCostPerStudent.toDisplayString()}"
                        )
                    }
                }
            }

            uiState.laysTotal?.also { laysTotal ->
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
                                Text("LAYS")
                                Text(
                                    style = MaterialTheme.typography.headlineLarge,
                                    text = "${laysTotal.laysFromPer100Currency.toDisplayString()}-${laysTotal.laysToPer100Currency.toDisplayString()} per 100 $currencySymbol $currencyCode",
                                )
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
                            Icon(Icons.AutoMirrored.Filled.ListAlt, contentDescription = null)
                        },
                        headlineContent = {
                            Text(intervention.name)
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
                    )
                }
            }


            plan.costCategories.forEach { category ->
                val isCollapsed = category.id in uiState.collapsedSectionIds

                item {
                    ListItem(
                        modifier = Modifier.clickable {
                            onToggleSectionIdCollapse(category.id)
                        },
                        headlineContent = { Text(category.name) },
                        trailingContent = {
                            Row {
                                uiState.costTotals[category.id]?.also { totals ->
                                    CostTotalsColumn(
                                        currencySymbol = currencySymbol,
                                        totals = totals
                                    )
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

                category.costs.takeIf { !isCollapsed }?.forEach { cost ->
                    val totals = uiState.costTotals[cost.id]

                    item {
                        ListItem(
                            leadingContent = {
                                Icon(
                                    Icons.Default.Receipt,
                                    contentDescription = null,
                                )
                            },
                            headlineContent = { Text(cost.name) },
                            supportingContent = {
                                Text(formatCost(cost, plan.currency))
                            },
                            trailingContent = {
                                Row {
                                    if(totals != null) {
                                        CostTotalsColumn(
                                            currencySymbol = currencySymbol,
                                            totals = totals,
                                        )
                                    }

                                    IconButton(
                                        onClick = { }
                                    ) {
                                        Icon(Icons.Outlined.Info, contentDescription = "Info")
                                    }
                                }

                            }
                        )
                    }
                }
            }
        }else {
            //show loading
        }


    }
}