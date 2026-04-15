package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.datetime.DateTimeUnit
import kotlinx.serialization.Serializable

@Serializable
enum class PreflightDateTimePeriodEnum(
    val id: String,
    val displayName: String,
    val dateTimeUnit: DateTimeUnit,
) {

    YEAR("year", "year(s)", DateTimeUnit.YEAR),
    MONTH("month", "month(s)", DateTimeUnit.MONTH),
    DAY("day", "day(s)", DateTimeUnit.DAY);
}