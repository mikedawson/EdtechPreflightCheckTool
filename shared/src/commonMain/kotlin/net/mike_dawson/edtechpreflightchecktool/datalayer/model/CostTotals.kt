package net.mike_dawson.edtechpreflightchecktool.datalayer.model

data class CostTotals(
    val forId: String,
    val totalCost: CostAmountRange,
    val totalMarginalCostPerStudent: CostAmountRange,
    val totalCostPerStudent: CostAmountRange,
    val percentageOfTotalTo: Float? = null,
    val percentageOfTotalFrom: Float? = null,
    val percentageOfTotalMarginalTo: Float? = null,
    val percentageOfTotalMarginalFrom: Float? = null,
)