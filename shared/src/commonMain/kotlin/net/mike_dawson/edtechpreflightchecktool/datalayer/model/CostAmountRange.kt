package net.mike_dawson.edtechpreflightchecktool.datalayer.model

import kotlinx.serialization.Serializable

@Serializable
data class CostAmountRange(
    val from: Float,
    val to: Float,
) {


    operator fun times(other: Float) : CostAmountRange {
        return CostAmountRange(from * other, to * other)
    }

    operator fun div(other: Float): CostAmountRange {
        return CostAmountRange(from / other, to / other)
    }

    operator fun div(other: Int): CostAmountRange {
        return CostAmountRange(from / other, to / other)
    }

    operator fun plus(other: CostAmountRange): CostAmountRange {
        return CostAmountRange(
            from = from + other.from,
            to = to + other.to,
        )
    }


}