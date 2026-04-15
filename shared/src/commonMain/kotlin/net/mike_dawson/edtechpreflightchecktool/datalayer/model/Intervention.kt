package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class Intervention(
    val id: String = Uuid.random().toString(),
    val name: String = "",
    val laysFrom: Float = 1f,
    val laysTo: Float = 2f,
    val licenseType: InterventionLicenseTypeEnum = InterventionLicenseTypeEnum.PROPRIETARY,
    val category: InterventionCategoryEnum = InterventionCategoryEnum.DPL,
    val inCountrySupported: Boolean = false,
    val oAuthSupported: Boolean = false,
    val xApiSupported: Boolean = false,
) {
}