package net.mike_dawson.edtechpreflightchecktool.nav

import kotlinx.serialization.Serializable

@Serializable
sealed interface PreflightAppDest

@Serializable
object PlanListDest: PreflightAppDest

@Serializable
data class PlanEditDest(
    val id: String,
): PreflightAppDest


