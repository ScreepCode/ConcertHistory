package de.buseslaar.concerthistory.views.onboarding.pages

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.buseslaar.concerthistory.R

@Composable
fun WelcomePage(
    modifier: Modifier = Modifier,
    onContinue: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WelcomeIcon()
        Spacer(modifier = Modifier.height(24.dp))

        WelcomeHeader()
        Spacer(modifier = Modifier.height(24.dp))

        WelcomeDescription()
        Spacer(modifier = Modifier.height(40.dp))

        ContinueButton(onContinue)
    }
}

@Composable
private fun WelcomeIcon() {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Icon(
        painter = painterResource(R.drawable.app_logo),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .size(80.dp)
            .scale(scale)
    )
}

@Composable
private fun WelcomeHeader() {
    Text(
        text = stringResource(R.string.onboarding_welcome_title),
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun WelcomeDescription() {
    Text(
        text = stringResource(R.string.onboarding_welcome_description),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(0.9f)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = stringResource(R.string.onboarding_welcome_details),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(0.9f)
    )
}

@Composable
private fun ContinueButton(onContinue: () -> Unit) {
    Button(
        onClick = onContinue,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(48.dp)
    ) {
        Text(
            text = stringResource(R.string.onboarding_continue),
            style = MaterialTheme.typography.titleMedium
        )
    }
}