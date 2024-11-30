package de.buseslaar.concerthistory.views.search


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
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
            SearchView()
        }
    }
}