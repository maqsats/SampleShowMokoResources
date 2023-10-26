package daniel.avila.rnm.kmm.presentation.ui.features.main.custom_keyboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomKeyboard(
    modifier: Modifier = Modifier,
    onKeyPress: (String) -> Unit,
    onClear: () -> Unit,
    onClearAll: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, bottom = 10.dp, end = 10.dp)
    ) {
        Spacer(modifier = Modifier.padding(vertical = 5.dp))

        val allKeys = listOf("7", "8", "9", "4", "5", "6", "1", "2", "3", ",", "0", "⌫")

        val keysPerRow = 3

        for (i in allKeys.indices step keysPerRow) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (j in i until minOf(i + keysPerRow, allKeys.size)) {

                    val key = allKeys[j]

                    KeyItem(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        text = key,
                        onKeyPress = onKeyPress,
                        isBackSpace = key == "⌫",
                        onClear = onClear,
                        onClearAll = onClearAll
                    )
                }
            }
        }
    }
}