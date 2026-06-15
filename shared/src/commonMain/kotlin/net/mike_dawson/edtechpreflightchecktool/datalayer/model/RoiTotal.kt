package net.mike_dawson.edtechpreflightchecktool.datalayer.model

data class RoiTotal(
    val unit: RoiUnitEnum,
    val from: Float,
    val to: Float,
    val roiFromPerCurrency: Float,
    val roiToPerCurrency: Float,
) {

    val roiFromPer100Currency: Float
        get() = roiFromPerCurrency * 100f

    val roiToPer100Currency: Float
        get() = roiToPerCurrency * 100f

}
