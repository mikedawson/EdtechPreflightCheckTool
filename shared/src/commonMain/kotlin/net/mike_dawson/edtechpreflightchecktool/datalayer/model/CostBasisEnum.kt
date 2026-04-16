package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.serialization.Serializable

@Serializable
enum class CostBasisEnum(
    val id: String,
    val displayName: String,
    val placeholderTrailingText: String? = null,
) {

    PER_STUDENT("per_student", "Per student", "student(s)"),
    PER_TEACHER("per_teacher", "Per teacher", "teachers(s)"),
    PER_SCHOOL("per_school", "Per school", "school(s)"),
    PER_USER("per_user", "Per user", "user(s)"),
    LUMP_SUM("lump_sum", "Fixed amount (lump sum)", null);

    val isUnitBased: Boolean
        get() = placeholderTrailingText != null


}