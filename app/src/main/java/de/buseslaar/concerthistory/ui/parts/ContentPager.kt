package de.buseslaar.concerthistory.ui.parts

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingContentPager(
    items: List<@Composable () -> Unit>,
    currentPage: Int,
    onPageChange: (Int) -> Unit,
    pagerColor: Color = MaterialTheme.colorScheme.primary,
    bottomPadding: Dp = 16.dp,
    pagerCircleSize: Dp = 5.dp
) {
    val pagerState = rememberPagerState(initialPage = currentPage) { items.size }

    LaunchedEffect(currentPage) {
        pagerState.animateScrollToPage(currentPage)
    }

    LaunchedEffect(pagerState.currentPage) {
        onPageChange(pagerState.currentPage)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            // Actual content
            Box(modifier = Modifier.fillMaxSize()) {
                items[page]()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottomPadding),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(items.size) { index ->
                Indicator(
                    isSelected = pagerState.currentPage == index,
                    pagerColor = pagerColor,
                    pagerCircleSize = pagerCircleSize
                )
            }
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean, pagerColor: Color, pagerCircleSize: Dp) {
    val width by animateDpAsState(targetValue = if (isSelected) pagerCircleSize * 4 else pagerCircleSize)

    Box(
        modifier = Modifier
            .padding(pagerCircleSize / 2)
            .height(pagerCircleSize)
            .width(width)
            .clip(CircleShape)
            .background(pagerColor)
    )
}
