package de.buseslaar.concerthistory.views.onboarding

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object OnboardingRootRoute

fun NavGraphBuilder.addOnboardingNavGraph(
    navController: NavHostController,
    onOnboardingCompleted: () -> Unit
) {
    composable<OnboardingRootRoute> {
        OnboardingScreen(
            onOnboardingCompleted = onOnboardingCompleted,
        )
    }
}