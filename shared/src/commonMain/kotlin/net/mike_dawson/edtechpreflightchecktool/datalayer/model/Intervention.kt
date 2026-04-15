package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.serialization.Serializable

@Serializable
data class Intervention(
    val id: String,
    val name: String,
    val laysFrom: Float = 1f,
    val laysTo: Float = 2f,
    val licenseType: InterventionLicenseTypeEnum = InterventionLicenseTypeEnum.PROPRIETARY,
    val category: InterventionCategoryEnum = InterventionCategoryEnum.DPL,
    val costs: List<Cost> = emptyList(),
) {
}