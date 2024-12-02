package de.buseslaar.concerthistory.views.search


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.buseslaar.concerthistory.views.artistDetails.ArtistDetails
import kotlinx.serialization.Serializable

@Serializable
object SearchRoot

@Serializable
object Search

fun NavGraphBuilder.addSearchNavGraph(navController: NavHostController) {
    navigation<SearchRoot>(
        startDestination = Search,
    ) {
        composable<Search> {
            SearchView(onDetailsClick = {
                navController.navigate(ArtistDetails(it))
            })
        }
    }
}