package de.buseslaar.concerthistory.views.artistDetails

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import de.buseslaar.concerthistory.views.setlistDetails.SetlistDetails
import kotlinx.serialization.Serializable

@Serializable
object ArtistDetailsRoot

@Serializable
object ArtistDetailsOverview

@Serializable
data class ArtistDetails(
    val artistMbId: String,
)

fun NavGraphBuilder.addArtistDetailsNavGraph(navController: NavHostController) {
    navigation<ArtistDetailsRoot>(
        startDestination = ArtistDetailsOverview,
    ) {
        composable<ArtistDetailsOverview> {

        }

        composable<ArtistDetails> {
            val args = it.toRoute<ArtistDetails>()

            ArtistDetailsView(
                selectedArtistMbId = args.artistMbId,
                navigateBack = {
                    navController.popBackStack()
                },
                onConcertClick = { concertId ->
                    navController.navigate(SetlistDetails(setlistId = concertId))
                }
            )
        }
    }
}