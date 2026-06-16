package net.mike_dawson.edtechpreflightchecktool.ext

import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostAmountRange

fun List<CostAmountRange>.sumCostRanges() : CostAmountRange {
    return CostAmountRange(
        from = this.sumOf { it.from.toDouble() }.toFloat(),
        to = this.sumOf { it.to.toDouble() }.toFloat(),
    )
}

