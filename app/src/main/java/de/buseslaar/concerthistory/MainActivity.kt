package de.buseslaar.concerthistory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import de.buseslaar.concerthistory.navigation.ConcertHistoryNavHost
import de.buseslaar.concerthistory.ui.theme.ConcertHistoryTheme
import de.buseslaar.concerthistory.views.mainView.MainView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConcertHistoryTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {
    val navController = rememberNavController()
    MainView(navController = navController) {
        ConcertHistoryNavHost(navController)
    }
}