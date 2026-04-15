package net.mike_dawson.edtechpreflightchecktool.components

import androidx.compose.runtime.Composable
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Cost
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostTypeEnum
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Currency

@Composable
fun formatCost(
    cost: Cost,
    currency: Currency,
): String {
    return buildString {
        append(currency.symbol)
        append(" ")
        append(cost.costAmount.toDisplayString())
        val costBasisTrailingText = cost.costBasis.placeholderTrailingText

        if(costBasisTrailingText != null) {
            append(" per ")
            append(cost.costBasisPerNum.toDisplayString())
            append(" ")
            append(costBasisTrailingText)
        }else {
            append(" (${cost.costBasis.displayName}) ")
        }

        when(cost.costType) {
            CostTypeEnum.EXPENSE -> {
                append(" per ")
                append(cost.recurrencePeriodQuantity)
                append(" ")
                append(cost.recurrencePeriodDurationUnit.displayName)
            }

            CostTypeEnum.ASSET -> {

            }
        }
    }
}