package de.buseslaar.concerthistory.views.setlistDetails

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
object SetlistDetailsRoot

@Serializable
object SetlistDetailsOverview

@Serializable
data class SetlistDetails(
    val setlistId: String,
)

fun NavGraphBuilder.addSetlistDetailsNavGraph(navController: NavHostController) {
    navigation<SetlistDetailsRoot>(
        startDestination = SetlistDetailsOverview,
    ) {
        composable<SetlistDetailsOverview> {

        }
        composable<SetlistDetails> {
            val args = it.toRoute<SetlistDetails>()
            SetlistDetailsView(
                selectedSetlistId = args.setlistId,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}