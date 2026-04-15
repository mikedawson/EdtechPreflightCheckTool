package net.mike_dawson.edtechpreflightchecktool.nav

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Cost
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Intervention

@Serializable
sealed interface PreflightAppDest

@Serializable
object PlanListDest: PreflightAppDest

@Serializable
data class PlanEditDest(
    val id: String?,
): PreflightAppDest

@Serializable
data class PlanDetailDest(
    val id: String
): PreflightAppDest


@Serializable
data class CostEditDest(
    val costStr: String,
    val currency: String,
): PreflightAppDest {

    @Transient
    val cost: Cost = Json.decodeFromString(Cost.serializer(), costStr)

    companion object {

        fun create(cost: Cost, currency: String) : CostEditDest{
            return CostEditDest(
                costStr = Json.encodeToString(Cost.serializer(), cost),
                currency = currency,
            )
        }

    }

}

@Serializable
data class InterventionEditDest(
    val interventionStr: String
): PreflightAppDest {

    @Transient
    val intervention: Intervention = Json.decodeFromString(Intervention.serializer(), interventionStr)

    companion object {

        fun create(intervention: Intervention) : InterventionEditDest {
            return InterventionEditDest(
                interventionStr = Json.encodeToString(Intervention.serializer(), intervention)
            )
        }
    }

}
