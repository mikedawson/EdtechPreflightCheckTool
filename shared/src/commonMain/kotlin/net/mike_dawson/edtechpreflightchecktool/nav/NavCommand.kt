package net.mike_dawson.edtechpreflightchecktool.nav

import kotlin.reflect.KClass
import kotlin.time.Clock

sealed class NavCommand(
    val timestamp: Long = Clock.System.now().toEpochMilliseconds(),
) {
    class Navigate(
        val destination: PreflightAppDest,
        val clearBackStack: Boolean = false,
        val popUpTo: PreflightAppDest? = null,
        val popUpToClass: KClass<*>? = null,
        val popUpToInclusive: Boolean = false,
        timestamp: Long = Clock.System.now().toEpochMilliseconds(),
    ) : NavCommand(timestamp)

    class PopToRoute(
        val destination: PreflightAppDest,
        val inclusive: Boolean,
        timestamp: Long = Clock.System.now().toEpochMilliseconds(),
    ): NavCommand(timestamp)

    class PopToRouteClass(
        val destination: KClass<*>,
        val inclusive: Boolean,
        timestamp: Long = Clock.System.now().toEpochMilliseconds(),
    ): NavCommand(timestamp)

    class PopUp(
        timestamp: Long = Clock.System.now().toEpochMilliseconds(),
    ): NavCommand(timestamp)


}

