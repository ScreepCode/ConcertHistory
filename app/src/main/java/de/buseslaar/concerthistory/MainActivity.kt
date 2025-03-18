package de.buseslaar.concerthistory

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import de.buseslaar.concerthistory.navigation.ConcertHistoryNavHost
import de.buseslaar.concerthistory.ui.theme.ConcertHistoryTheme
import de.buseslaar.concerthistory.views.mainView.MainView
import initializeAppContextHolder

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val splashScreenDuration = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize()
        initializeAppContextHolder()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                val elapsedTime = System.currentTimeMillis() - startTime
                val minDurationReached = elapsedTime >= splashScreenDuration
                !viewModel.isReady.value || !minDurationReached
            }
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }

        enableEdgeToEdge()
        setContent {
            ConcertHistoryTheme {
                AppContent(isOnboardingCompleted = viewModel.isOnboardingCompleted.value)
            }
        }
    }

    private val startTime = System.currentTimeMillis()
}

@Composable
fun AppContent(isOnboardingCompleted: Boolean) {
    val navController = rememberNavController()
    MainView(navController = navController) {
        ConcertHistoryNavHost(navController, isOnboardingCompleted = isOnboardingCompleted)
    }

}