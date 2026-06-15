package net.mike_dawson.edtechpreflightchecktool

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import net.mike_dawson.edtechpreflightchecktool.nav.AboutDest
import net.mike_dawson.edtechpreflightchecktool.nav.PlanListDest
import net.mike_dawson.edtechpreflightchecktool.nav.PreflightAppDest

enum class Destination(
    val route: PreflightAppDest,
    val label: String,
    val icon: ImageVector,
) {

    PLANNER(PlanListDest, "Planner", Icons.Default.Calculate),

    ABOUT(AboutDest, "About", Icons.Default.Info)

}

