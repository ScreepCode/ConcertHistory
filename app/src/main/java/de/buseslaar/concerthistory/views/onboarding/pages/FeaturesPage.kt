package de.buseslaar.concerthistory.views.onboarding.pages

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.buseslaar.concerthistory.R

@Composable
fun FeaturesPage(
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
        FeaturePageHeader()
        Spacer(modifier = Modifier.height(16.dp))

        FeaturesList()
        Spacer(modifier = Modifier.height(40.dp))

        ContinueButton(onContinue)
    }
}

@Composable
private fun FeaturePageHeader() {
    Text(
        text = stringResource(R.string.onboarding_features_title),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
private fun FeaturesList() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FeatureItem(
            icon = Icons.Default.Star,
            text = stringResource(R.string.onboarding_feature_1),
            color = MaterialTheme.colorScheme.tertiary
        )

        FeatureItem(
            icon = Icons.Default.Search,
            text = stringResource(R.string.onboarding_feature_2),
            color = MaterialTheme.colorScheme.secondary
        )

        FeatureItem(
            icon = Icons.Default.Favorite,
            text = stringResource(R.string.onboarding_feature_3),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun FeatureItem(
    icon: ImageVector,
    text: String,
    color: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            FeatureIcon(icon, color)

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
private fun FeatureIcon(icon: ImageVector, color: Color) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .border(2.dp, color, RoundedCornerShape(8.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
    }
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