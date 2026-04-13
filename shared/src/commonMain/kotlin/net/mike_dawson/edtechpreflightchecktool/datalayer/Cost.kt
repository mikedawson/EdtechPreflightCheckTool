package net.mike_dawson.edtechpreflightchecktool.datalayer

import kotlinx.datetime.DateTimeUnit
import kotlinx.serialization.Serializable


@Serializable
class Cost(
    val id: String,
    val name: String,
    val costType: CostTypeEnum = CostTypeEnum.EXPENSE,
    val recurrent: Boolean = false,
    val recurrencePeriodQuantity: Int = 1,
    val recurrencePeriodDurationUnit : DateTimeUnit = DateTimeUnit.YEAR,
    val costBasis: CostBasisEnum = CostBasisEnum.PER_STUDENT,
    val costBasisPerNum: Float = 1f,
    val costAmount: Float = 1f,
    val assetLifespanYears: Float = 4f,
    val assetDisposalCost: Float = 0f,
) {
}