package de.buseslaar.concerthistory.ui.parts.tabSwitch

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TabSwitch(tabs: List<TabElement>, selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = { Text(tab.label) },
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) }
                )
            }

        }
        tabs[selectedTabIndex].screen()
    }
}