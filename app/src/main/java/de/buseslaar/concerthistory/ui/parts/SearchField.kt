package de.buseslaar.concerthistory.ui.parts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import de.buseslaar.concerthistory.R

@Composable
fun SearchField(
    onEnterPress: () -> Unit,
    onValueChange: (String) -> Unit,
    placeholder: String,
    value: String,
    textFieldFocused: Boolean,
    onTextFieldFocusedChange: (Boolean) -> Unit = {},
) {
    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    TextField(
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused != textFieldFocused) {
                    onTextFieldFocusedChange(it.isFocused)
                }
            },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onEnterPress()
        }),
        leadingIcon = {
            IconButton(
                content = {
                    Icon(
                        if (textFieldFocused) {
                            Icons.AutoMirrored.Filled.ArrowBack
                        } else {
                            Icons.Default.Search
                        }, contentDescription = stringResource(R.string.search)
                    )
                },
                onClick = {
                    if (textFieldFocused) {
                        focusManager.clearFocus()
                    }
                }

            )
        },

        shape = RoundedCornerShape(16.dp),
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
    )

}
