package de.buseslaar.concerthistory.views.visited

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.buseslaar.concerthistory.views.setlistDetails.SetlistDetails
import kotlinx.serialization.Serializable

@Serializable
object VisitedRoot

@Serializable
object VisitedOverview


fun NavGraphBuilder.addVisitedNavGraph(navController: NavHostController) {
    navigation<VisitedRoot>(
        startDestination = VisitedOverview,
    ) {
        composable<VisitedOverview> {
            VisitedView(
                onShowDetails = { setlistId -> navController.navigate(SetlistDetails(setlistId = setlistId)) },
            )
        }
    }
}