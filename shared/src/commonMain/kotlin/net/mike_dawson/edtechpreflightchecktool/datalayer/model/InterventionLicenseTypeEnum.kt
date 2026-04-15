package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.serialization.Serializable

@Serializable
enum class InterventionLicenseTypeEnum(val id: String) {

    PROPRIETARY("proprietary"), OPEN_SOURCE("opensource")

}