package de.buseslaar.concerthistory.views.dashboard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object DashboardRoot

@Serializable
object DashboardOverview


fun NavGraphBuilder.addDashboardNavGraph(navController: NavHostController) {
    navigation<DashboardRoot>(
        startDestination = DashboardOverview,
    ) {
        composable<DashboardOverview> {
            DashboardView()
        }
    }
}