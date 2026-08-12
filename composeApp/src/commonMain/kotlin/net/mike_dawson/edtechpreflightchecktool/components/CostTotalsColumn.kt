package net.mike_dawson.edtechpreflightchecktool.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostTotals

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CostTotalsColumn(
    currencyCode: String,
    totals: CostTotals,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
){
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
    ) {
        Text(
            text = "${totals.totalCost.toDisplayString(currencyCode = currencyCode)}/yr",
            style = MaterialTheme.typography.bodyMediumEmphasized,
            textAlign = textAlign
        )
        Text(
            "Marginal cost/student/year ($currencyCode): ${totals.totalMarginalCostPerStudent.toDisplayString()}",
            textAlign = textAlign,
        )
        Text(
            "Cost/student/year ($currencyCode): ${totals.totalCostPerStudent.toDisplayString()}",
            textAlign = textAlign,
        )
    }
}