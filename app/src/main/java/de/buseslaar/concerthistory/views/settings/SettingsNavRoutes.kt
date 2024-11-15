package de.buseslaar.concerthistory.views.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SettingsOverview

fun NavGraphBuilder.addSettingsNavGraph(navController: NavHostController) {
    composable<SettingsOverview> {
        SettingsView(onBack = { navController.popBackStack() })
    }
}