package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.datetime.DateTimeUnit
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.Uuid

@Serializable
data class Plan(
    val id: String = Uuid.random().toString(),
    val currency: Currency = Currency(
        symbol = "$",
        name = "US Dollar",
        symbolNative = "$",
        decimalDigits = 2,
        rounding = 0,
        code = "USD",
        name_plural = "US dollars",
    ),
    val name: String = "",
    val country: String = "",
    val averageStudentsPerClass: Float = 30f,
    val averageClassesPerSchool: Float = 10f,
    val targetNumStudents: Int = 50_000,
    val interventions: List<Intervention> = emptyList(),
    val costCategories: List<CostCategory> = listOf(
        CostCategory("Infrastructure"),
        CostCategory("Training and support"),
        CostCategory("Other costs"),
    ),
    val durationNum: Int = 1,
    val durationUnit: DateTimeUnit = DateTimeUnit.YEAR,
    val lastModified: Instant = Clock.System.now(),
)
