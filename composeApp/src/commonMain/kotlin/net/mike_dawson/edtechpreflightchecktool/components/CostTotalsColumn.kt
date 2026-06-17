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
    currencySymbol: String,
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
            text = "${totals.totalCost.toDisplayString(currencySymbol = currencySymbol)}/yr",
            style = MaterialTheme.typography.bodyMediumEmphasized,
            textAlign = textAlign
        )
        Text(
            "Marginal cost/student/year: ${totals.totalMarginalCostPerStudent.toDisplayString(currencySymbol =  currencySymbol)}",
            textAlign = textAlign,
        )
        Text(
            "Cost/student/year: ${totals.totalCostPerStudent.toDisplayString(currencySymbol =  currencySymbol)}",
            textAlign = textAlign,
        )
    }
}