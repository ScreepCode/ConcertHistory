package de.buseslaar.concerthistory.views.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.ui.parts.OnboardingContentPager
import de.buseslaar.concerthistory.views.onboarding.pages.FeaturesPage
import de.buseslaar.concerthistory.views.onboarding.pages.LoginPage
import de.buseslaar.concerthistory.views.onboarding.pages.WelcomePage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onOnboardingCompleted: () -> Unit,
) {
    val onboardingViewModel: OnboardingViewModel = viewModel()
    var currentPage by remember { mutableIntStateOf(0) }
    var showSkipDialog by remember { mutableStateOf(false) }

    val pages = createPages(
        currentPage = currentPage,
        onContinue = { currentPage++ },
        onOnboardingCompleted = onOnboardingCompleted,
        onboardingViewModel = onboardingViewModel
    )

    Scaffold(
        topBar = {
            OnboardingTopBar(
                currentPage = currentPage,
                onNavigateBack = { currentPage-- },
                onSkip = { showSkipDialog = true }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            OnboardingContentPager(
                items = pages,
                currentPage = currentPage,
                onPageChange = { newPage -> currentPage = newPage }
            )
        }
    }

    if (showSkipDialog) {
        SkipOnboardingDialog(
            onConfirm = {
                showSkipDialog = false
                onboardingViewModel.setOnboardingCompleted(true)
                onOnboardingCompleted()
            },
            onDismiss = { showSkipDialog = false }
        )
    }
}

@Composable
private fun createPages(
    currentPage: Int,
    onContinue: () -> Unit,
    onOnboardingCompleted: () -> Unit,
    onboardingViewModel: OnboardingViewModel
): List<@Composable () -> Unit> {
    return listOf(
        {
            WelcomePage(
                onContinue = onContinue
            )
        },
        {
            FeaturesPage(
                onContinue = onContinue
            )
        },
        {
            LoginPage(
                onLogin = {
                    onboardingViewModel.setSetlistUsername(it)
                    onboardingViewModel.setOnboardingCompleted(true)
                    onOnboardingCompleted()
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OnboardingTopBar(
    currentPage: Int,
    onNavigateBack: () -> Unit,
    onSkip: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {},
        navigationIcon = {
            if (currentPage > 0) {
                IconButton(
                    onClick = onNavigateBack
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.desc_back),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        actions = {
            SkipButton(onSkip = onSkip)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
private fun SkipButton(onSkip: () -> Unit) {
    TextButton(
        onClick = onSkip,
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.onboarding_skip),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun SkipOnboardingDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.onboarding_skip_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        },
        text = {
            Text(
                text = stringResource(R.string.onboarding_skip_message),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = stringResource(R.string.onboarding_skip_confirm),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = stringResource(R.string.onboarding_skip_cancel),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    )
}