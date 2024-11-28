package de.buseslaar.concerthistory.views.favorites

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object FavoritesRoot

@Serializable
object FavoritesOverview

fun NavGraphBuilder.addFavoritesNavGraph(navController: NavHostController) {

    composable<FavoritesOverview> {
        FavoritesView(

        )
    }
}