package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Currency(
    val symbol: String,
    val name: String,
    @SerialName("symbol_native")
    val symbolNative: String,
    @SerialName("decimal_digits")
    val decimalDigits: Int,
    val rounding: Int,
    val code: String,
    val name_plural: String
) {
}