package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.datetime.DateTimeUnit

enum class CostRecurrencePeriodEnum(
    val id: String,
    val displayName: String,
    val dateTimeUnit: DateTimeUnit,
) {

    YEAR("year", "year(s)", DateTimeUnit.YEAR),
    MONTH("month", "month(s)", DateTimeUnit.MONTH),
    DAY("day", "day(s)", DateTimeUnit.DAY);
}