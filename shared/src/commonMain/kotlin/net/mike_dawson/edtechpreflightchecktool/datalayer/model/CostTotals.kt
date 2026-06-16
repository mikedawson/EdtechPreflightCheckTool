package net.mike_dawson.edtechpreflightchecktool.datalayer.model

class CostTotals(
    val forId: String,
    val totalCost: CostAmountRange,
    val totalMarginalCostPerStudent: CostAmountRange,
    val totalCostPerStudent: CostAmountRange,
)