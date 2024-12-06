package de.buseslaar.concerthistory

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToString
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConcertHistoryTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.launchConcertHistoryApp(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun app_launches() {
        println(composeTestRule.onRoot().printToString())
        composeTestRule.onNodeWithText("Concert History").assertExists()
    }
}