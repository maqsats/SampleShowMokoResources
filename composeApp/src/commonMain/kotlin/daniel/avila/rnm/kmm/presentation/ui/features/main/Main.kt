package daniel.avila.rnm.kmm.presentation.ui.features.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.presentation.ui.features.main.custom_main_tab.CustomTabBar
import daniel.avila.rnm.kmm.presentation.ui.features.main.custom_main_tab.TabItem
import daniel.avila.rnm.kmm.presentation.ui.features.main.exchange_list_main.ExchangeListMain

@Composable
fun Main(modifier: Modifier = Modifier) {
    var inputText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                "100",
                TextRange(3)
            )
        )
    }
    var selectedTab by remember { mutableStateOf(TabItem("", false)) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        CustomTabBar(modifier = Modifier.fillMaxWidth()) {
            selectedTab = it
        }
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .wrapContentHeight()
                .focusRequester(focusRequester),
            value = inputText,
            onValueChange = { input ->
                when {
                    input.text.length > 10 -> {

                    }
                    input.text.isEmpty() || input.text == "00" -> {
                        inputText = input.copy(text = "0", TextRange(1))
                    }
                    else -> inputText =
                        input.copy(input.text.filter { it.isDigit() }.trimStart('0'))
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                autoCorrect = false
            ),
            cursorBrush = Brush.verticalGradient(
                0.0f to Color.Transparent,
                0.15f to Color.Transparent,
                0.15f to Color.Black,
                0.85f to Color.Black,
                0.85f to Color.Transparent,
                1.00f to Color.Transparent
            ),
            maxLines = 1,
            textStyle = MaterialTheme.typography.body2.copy(textAlign = TextAlign.End),
        )
        ExchangeListMain(modifier = Modifier.weight(1f), selectedTab.buyOrSell)
    }
}