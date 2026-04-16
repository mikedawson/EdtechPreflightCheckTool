package net.mike_dawson.edtechpreflightchecktool.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
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
import net.mike_dawson.edtechpreflightchecktool.components.formatCost
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
    val plan = uiState.plan

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        if(plan != null) {
            item {
                FlowRow(
                    modifier = Modifier.padding(16.dp)
                ) {
                    uiState.costTotals[PlanDetailViewModel.ID_TOTAL]?.also { grandTotals ->
                        //As per https://developer.android.com/develop/ui/compose/components/card?_gl=1*1ij2cas*_ga*MTY5MjA3MzQ2Mi4xNzU4NjUzOTc0*_ga_QPQ2NRV856*czE3NzYzMjYwMjMkbzI5JGcxJHQxNzc2MzI2MDQ0JGozOSRsMCRoMA..
                        Card(
                            colors = CardDefaults.outlinedCardColors(),
                            border = BorderStroke(1.dp, Color.Black),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Total Cost/year")
                                Text(
                                    style = MaterialTheme.typography.headlineLarge,
                                    text = "$currencySymbol ${grandTotals.totalCost}"
                                )
                            }
                        }
                    }
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