package de.buseslaar.concerthistory

import AppContextHolder
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import de.buseslaar.concerthistory.data.database.FavoritesDatabaseProvider
import de.buseslaar.concerthistory.data.datastore.DataStoreServiceProvider

fun ComposeContentTestRule.launchConcertHistoryApp(context: Context) {
    if (Looper.myLooper() == null) {
        Looper.prepare()
    }

    Handler(Looper.getMainLooper()).post {
        DataStoreServiceProvider.initialize(context)
        FavoritesDatabaseProvider.initialize(context)
        AppContextHolder.getInstance().apply {
            setApplicationContext(context)
            setActiveActivity(ComponentActivity()) // dummy activity
        }

        setContent {
            AppContent()
        }
    }
}

fun ComposeContentTestRule.waitUntilNodeIsDisplayed(
    text: String,
    timeoutMillis: Long = 5000
) {
    this.waitUntil(timeoutMillis = timeoutMillis) {
        this.onAllNodesWithText(text)
            .fetchSemanticsNodes(atLeastOneRootRequired = true).isNotEmpty()
    }
}

fun ComposeContentTestRule.waitUntilNodeWithTestTagIsDisplayed(
    tag: String,
    timeoutMillis: Long = 5000
) {
    this.waitUntil(timeoutMillis = timeoutMillis) {
        this.onAllNodesWithTag(tag)
            .fetchSemanticsNodes(atLeastOneRootRequired = true).size == 1
    }
}