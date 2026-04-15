package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.serialization.Serializable

@Serializable
data class CostCategory(
    val name: String,
    val costs: List<Cost> = emptyList(),
)