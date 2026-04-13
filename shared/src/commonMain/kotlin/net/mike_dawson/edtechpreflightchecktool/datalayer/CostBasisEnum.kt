package net.mike_dawson.edtechpreflightchecktool.datalayer

import kotlinx.serialization.Serializable

@Serializable
enum class CostBasisEnum(val id: String, val displayName: String) {

    PER_STUDENT("per_student", "Per Student"),
    PER_TEACHER("per_teacher", "Per Teacher"),
    PER_SCHOOL("per_school", "Per School"),
    PER_USER("per_user", "Per User"),
    LUMP_SUM("lump_sum", "Lump sum")

}