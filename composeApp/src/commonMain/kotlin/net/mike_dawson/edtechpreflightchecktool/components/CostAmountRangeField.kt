package net.mike_dawson.edtechpreflightchecktool.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostAmountRange

fun CostAmountRange.toDisplayString(
    decimalPlaces: Int = 2,
    currencySymbol: String? = null,
): String {
    return "${from.toDisplayString(decimalPlaces, currencySymbol = currencySymbol)} to " +
            to.toDisplayString(decimalPlaces, currencySymbol = currencySymbol)
}
@Composable
fun CostAmountRangeField(
    modifier: Modifier,
    value: CostAmountRange,
    onValueChange: (CostAmountRange) -> Unit,
    label: (@Composable () -> Unit)?,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UstadNumberTextField(
            modifier = Modifier.weight(0.5f).padding(vertical = 8.dp),
            value = value.from,
            onValueChange = {
                onValueChange(value.copy(from = it))
            },
            label = label,
        )
        Spacer(Modifier.width(8.dp))
        Text("-")
        Spacer(Modifier.width(8.dp))
        UstadNumberTextField(
            modifier = Modifier.weight(0.5f).padding(vertical = 8.dp),
            value = value.to,
            onValueChange = {
                onValueChange(value.copy(to = it))
            },
            label = { Text("") }
        )
    }
}