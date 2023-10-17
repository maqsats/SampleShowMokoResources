package daniel.avila.rnm.kmm.presentation.ui.features.main.custom_keyboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.MR
import daniel.avila.rnm.kmm.presentation.ui.common.RoundedBackground
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun CustomKeyboard(
    modifier: Modifier = Modifier,
    onKeyPress: (String) -> Unit,
    onClear: () -> Unit,
    onClearAll: () -> Unit,
    onPaste: (String) -> Unit,
    onClose: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            RoundedBackground(
                modifier = Modifier
                    .wrapContentWidth(),
                onClick = onClose,
                content = {
                    Icon(
                        painter = painterResource(MR.images.close),
                        contentDescription = null
                    )
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            RoundedBackground(
                modifier = Modifier
                    .wrapContentWidth(),
                onClick = {
                    ClipboardHelper.getTextFromClipboard(clipboardManager)?.let {
                        onPaste(it)
                    }
                },
                content = {
                    Icon(
                        painter = painterResource(MR.images.paste),
                        contentDescription = null
                    )
                }
            )

            Spacer(modifier = Modifier.padding(horizontal = 10.dp))

            RoundedBackground(
                modifier = Modifier
                    .wrapContentWidth(),
                backgroundColor = MaterialTheme.colors.primaryVariant,
                onClick = onClear,
                onLongClick = onClearAll,
                content = {
                    Icon(
                        painter = painterResource(MR.images.clear),
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        // Define the keys in the order you want to display them
        val allKeys = listOf("7", "8", "9", "4", "5", "6", "1", "2", "3", "00", "0", ",")

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
                        onKeyPress = onKeyPress
                    )
                }
            }
        }
    }
}