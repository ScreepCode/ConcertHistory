package de.buseslaar.concerthistory.views.onboarding.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.buseslaar.concerthistory.BuildConfig
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.utils.extensions.clearFocusOnKeyboardDismiss
import kotlinx.coroutines.delay

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    onLogin: (String) -> Unit
) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val uriHandler = LocalUriHandler.current

    // Animation states
    var showHeader by remember { mutableStateOf(false) }
    var showInput by remember { mutableStateOf(false) }
    var showButtons by remember { mutableStateOf(false) }
    var showFooter by remember { mutableStateOf(false) }

    // Trigger animations sequentially
    LaunchedEffect(Unit) {
        showHeader = true
        delay(300)
        showInput = true
        delay(300)
        showButtons = true
        delay(300)
        showFooter = true
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (isFocused) {
                        focusManager.clearFocus()
                        isFocused = false
                    }
                }
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderSection(showHeader)

                UsernameInputSection(
                    username = username,
                    onUsernameChange = { username = it },
                    focusRequester = focusRequester,
                    onFocusChanged = { isFocused = it },
                    focusManager = focusManager,
                    showInput = showInput
                )

                ButtonsSection(
                    username = username,
                    showButtons = showButtons,
                    onLogin = onLogin
                )
            }

            FooterSection(
                showFooter = showFooter,
                uriHandler = uriHandler
            )
        }
    }
}

@Composable
private fun HeaderSection(showHeader: Boolean) {
    AnimatedVisibility(
        visible = showHeader,
        enter = fadeIn() + slideInVertically(initialOffsetY = { -100 })
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.onboarding_login_title),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.onboarding_login_description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.9f)
            )
        }
    }
}

@Composable
private fun UsernameInputSection(
    username: TextFieldValue,
    onUsernameChange: (TextFieldValue) -> Unit,
    focusRequester: FocusRequester,
    onFocusChanged: (Boolean) -> Unit,
    focusManager: FocusManager,
    showInput: Boolean
) {
    AnimatedVisibility(
        visible = showInput,
        enter = fadeIn() + slideInVertically(initialOffsetY = { 100 })
    ) {
        Column {
            OutlinedTextField(
                value = username,
                onValueChange = onUsernameChange,
                label = { Text(stringResource(R.string.onboarding_login_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clearFocusOnKeyboardDismiss()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        onFocusChanged(focusState.isFocused)
                    },
                supportingText = {
                    Text(
                        text = stringResource(R.string.onboarding_login_helper),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )
        }
    }
}

@Composable
private fun ButtonsSection(
    username: TextFieldValue,
    showButtons: Boolean,
    onLogin: (String) -> Unit
) {
    AnimatedVisibility(
        visible = showButtons,
        enter = fadeIn() + slideInVertically(initialOffsetY = { 100 })
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Login button
            Button(
                enabled = username.text.isNotEmpty(),
                onClick = {
                    onLogin(username.text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(
                        R.string.onboarding_login_button,
                        username.text.ifEmpty { "..." }),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // Demo button
            OutlinedButton(
                enabled = true,
                onClick = {
                    onLogin(BuildConfig.DEMO_USER)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(
                        R.string.onboarding_login_demo,
                        BuildConfig.DEMO_USER
                    ),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun FooterSection(
    showFooter: Boolean,
    uriHandler: UriHandler
) {
    AnimatedVisibility(
        visible = showFooter,
        enter = fadeIn() + slideInVertically(initialOffsetY = { 100 })
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.onboarding_login_no_account),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = { uriHandler.openUri("https://www.setlist.fm/signup") },
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        Text(
                            text = stringResource(R.string.onboarding_login_register),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(bottom = 16.dp))
        }
    }
}