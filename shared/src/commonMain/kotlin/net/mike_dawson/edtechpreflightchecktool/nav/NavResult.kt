package net.mike_dawson.edtechpreflightchecktool.nav

import kotlin.time.Clock


data class NavResult(
    val key: String,
    val timestamp: Long = Clock.System.now().toEpochMilliseconds(),
    val result: Any?
)