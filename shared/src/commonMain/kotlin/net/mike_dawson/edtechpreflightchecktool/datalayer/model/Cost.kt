package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.serialization.Serializable


@Serializable
data class Cost(
    val id: String,
    val name: String,
    val costType: CostTypeEnum = CostTypeEnum.EXPENSE,
    val recurrent: Boolean = false,
    val recurrencePeriodQuantity: Int = 1,
    val recurrencePeriodDurationUnit : PreflightDateTimePeriodEnum = PreflightDateTimePeriodEnum.YEAR,
    val costBasis: CostBasisEnum = CostBasisEnum.PER_STUDENT,
    val costBasisPerNum: Float = 1f,
    val costAmount: Float = 1f,
    val assetLifespanQuantity: Float = 4f,
    val assetLifespanUnit: PreflightDateTimePeriodEnum = PreflightDateTimePeriodEnum.YEAR,
    val assetDisposalCost: Float = 0f,
) {
}