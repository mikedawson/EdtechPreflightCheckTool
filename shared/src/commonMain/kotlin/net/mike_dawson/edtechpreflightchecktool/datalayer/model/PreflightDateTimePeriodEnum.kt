package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.datetime.DateTimeUnit
import kotlinx.serialization.Serializable

@Serializable
enum class PreflightDateTimePeriodEnum(
    val id: String,
    val displayName: String,
    val dateTimeUnit: DateTimeUnit,
    val unitsPerYear: Int,
) {

    YEAR("year", "year(s)", DateTimeUnit.YEAR, 1),
    MONTH("month", "month(s)", DateTimeUnit.MONTH, 12),
    DAY("day", "day(s)", DateTimeUnit.DAY, 365);
}