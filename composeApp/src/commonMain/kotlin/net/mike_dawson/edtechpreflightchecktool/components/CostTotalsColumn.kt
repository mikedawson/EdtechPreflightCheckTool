package net.mike_dawson.edtechpreflightchecktool.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostTotals

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CostTotalsColumn(
    currencySymbol: String,
    totals: CostTotals,
){
    Column(
        modifier = Modifier.width(256.dp),
    ) {
        Text(
            modifier = Modifier.width(256.dp),
            text = "$currencySymbol ${totals.totalCost.toDisplayString()}/yr",
            style = MaterialTheme.typography.bodyMediumEmphasized,
            textAlign = TextAlign.Right,
        )
        Text(
            "Marginal cost/student/year: $currencySymbol ${totals.totalMarginalCostPerStudent.toDisplayString()}",
            modifier = Modifier.width(256.dp),
            textAlign = TextAlign.Right,
        )
        Text(
            "Cost/student/year: $currencySymbol ${totals.totalCostPerStudent.toDisplayString()}",
            modifier = Modifier.width(256.dp),
            textAlign = TextAlign.Right,
        )
    }
}