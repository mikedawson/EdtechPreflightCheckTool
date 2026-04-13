package net.mike_dawson.edtechpreflightchecktool.datalayer

import kotlinx.serialization.Serializable

@Serializable
enum class InterventionCategoryEnum(val id: String, val displayName: String) {

    DPL("dpl", "Digital Personalised Learning"),

    TPD("tpd", "Teacher Professional Development"),

    OTHER("other", "Other"),

}