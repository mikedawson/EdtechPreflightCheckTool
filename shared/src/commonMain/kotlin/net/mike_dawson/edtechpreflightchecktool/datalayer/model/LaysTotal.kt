package net.mike_dawson.edtechpreflightchecktool.datalayer.model

data class LaysTotal(
    val from: Float,
    val to: Float,
    val laysFromPerCurrency: Float,
    val laysToPerCurrency: Float,
) {

    val laysFromPer100Currency: Float
        get() = laysFromPerCurrency * 100f

    val laysToPer100Currency: Float
        get() = laysToPerCurrency * 100f

}
