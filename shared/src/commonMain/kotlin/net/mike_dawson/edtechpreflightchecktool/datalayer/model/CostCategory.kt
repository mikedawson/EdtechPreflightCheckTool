package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class CostCategory(
    val name: String,
    val id: String = Uuid.random().toString(),
    val costs: List<Cost> = emptyList(),
)