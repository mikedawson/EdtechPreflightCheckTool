package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.serialization.Serializable

@Serializable
enum class CostBasisEnum(
    val id: String,
    val displayName: String,
    val placeholderTrailingText: String? = null,
) {

    PER_STUDENT("per_student", "Per Student", "student(s)"),
    PER_TEACHER("per_teacher", "Per Teacher", "teachers(s)"),
    PER_SCHOOL("per_school", "Per School", "school(s)"),
    PER_USER("per_user", "Per User", "user(s)"),
    LUMP_SUM("lump_sum", "Lump sum", null);

    val isUnitBased: Boolean
        get() = placeholderTrailingText != null


}