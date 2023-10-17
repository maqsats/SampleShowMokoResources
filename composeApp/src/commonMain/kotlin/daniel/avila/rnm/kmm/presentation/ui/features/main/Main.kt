package daniel.avila.rnm.kmm.presentation.ui.features.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.presentation.ui.features.main.custom_keyboard.CustomKeyboard
import daniel.avila.rnm.kmm.presentation.ui.features.main.custom_main_tab.CustomTabBar
import daniel.avila.rnm.kmm.presentation.ui.features.main.exchange_list_main.ExchangeListMain

@Composable
fun Main(modifier: Modifier = Modifier, onEmptyScreenClick: () -> Unit) {
    var isKeyboardVisible by remember { mutableStateOf(true) }
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        CustomTabBar(modifier = Modifier.fillMaxWidth())
        Text(
            text = inputText,
            maxLines = 1, // Set the maximum number of lines you want to display
            overflow = TextOverflow.Ellipsis, // Use ellipsis to indicate truncated text
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(100.dp)
                .clickable(onClick = {
                    isKeyboardVisible = true
                    onEmptyScreenClick()
                })
        )

        if (!isKeyboardVisible) {
            ExchangeListMain()
        } else {
            CustomKeyboard(
                modifier = Modifier.weight(1f),
                onKeyPress = {
                    inputText += it
                },
                onClear = { inputText = inputText.dropLast(1) },
                onClearAll = { inputText = "" },
                onPaste = { inputText = it },
                onClose = { isKeyboardVisible = false },
            )
        }
    }
}