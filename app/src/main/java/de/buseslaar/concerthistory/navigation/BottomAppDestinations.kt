package de.buseslaar.concerthistory.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import de.buseslaar.concerthistory.views.dashboard.DashboardRoot

enum class BottomAppDestinations(
    val route: Any,
    val label: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector
) {
    BottomDashboard(DashboardRoot, "Dashboard", Icons.Filled.Home, Icons.Outlined.Home),
}