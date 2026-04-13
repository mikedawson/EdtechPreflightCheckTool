package net.mike_dawson.edtechpreflightchecktool.datalayer

import kotlinx.datetime.DateTimeUnit
import kotlinx.serialization.Serializable

@Serializable
data class Plan(
    val id: String,
    val name: String,
    val country: String,
    val averageStudentsPerClass: Float = 30f,
    val averageClassesPerSchool: Float = 10f,
    val targetNumStudents: Int = 50_000,
    val interventions: List<Intervention> = emptyList(),
    val costCategories: List<CostCategory> = listOf(
        CostCategory("Infrastructure"),
        CostCategory("Training and support"),
        CostCategory("Other costs")
    ),
    val durationNum: Int = 1,
    val durationUnit: DateTimeUnit = DateTimeUnit.YEAR,
)
