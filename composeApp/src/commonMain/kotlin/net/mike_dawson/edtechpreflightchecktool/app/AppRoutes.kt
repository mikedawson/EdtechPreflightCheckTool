package net.mike_dawson.edtechpreflightchecktool.app

import kotlinx.serialization.Serializable

@Serializable
sealed interface PreflightAppDest

@Serializable
object PlanListDest: PreflightAppDest




