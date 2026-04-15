package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.serialization.Serializable

@Serializable
enum class InterventionLicenseTypeEnum(
    val id: String,
    val displayName: String,
) {

    PROPRIETARY("proprietary", "Proprietary"), OPEN_SOURCE("opensource", "Open Source")

}