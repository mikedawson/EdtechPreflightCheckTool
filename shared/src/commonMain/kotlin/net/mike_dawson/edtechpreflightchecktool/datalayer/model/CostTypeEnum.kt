package net.mike_dawson.edtechpreflightchecktool.datalayer.model

enum class CostTypeEnum(
    val id: String,
    val displayName: String,
) {

    EXPENSE("expense", "Expense"), ASSET("asset", "Asset")

}