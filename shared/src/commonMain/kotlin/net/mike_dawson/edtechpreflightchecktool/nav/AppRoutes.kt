package net.mike_dawson.edtechpreflightchecktool.nav

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Cost

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
): PreflightAppDest {

    @Transient
    val cost: Cost = Json.decodeFromString(Cost.serializer(), costStr)

    companion object {

        fun create(cost: Cost) : CostEditDest{
            return CostEditDest(
                costStr = Json.encodeToString(Cost.serializer(), cost)
            )
        }

    }

}