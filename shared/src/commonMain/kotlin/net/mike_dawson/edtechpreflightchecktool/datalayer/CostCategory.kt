package net.mike_dawson.edtechpreflightchecktool.datalayer

import kotlinx.serialization.Serializable

@Serializable
data class CostCategory(
    val name: String,
    val costs: List<Cost> = emptyList(),
) {
}