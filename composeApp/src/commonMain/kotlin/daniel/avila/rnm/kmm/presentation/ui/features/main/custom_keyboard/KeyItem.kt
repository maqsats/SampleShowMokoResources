package daniel.avila.rnm.kmm.presentation.ui.features.main.custom_keyboard

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.MR
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun KeyItem(
    modifier: Modifier = Modifier,
    text: String,
    isBackSpace: Boolean = false,
    onKeyPress: (String) -> Unit,
    onClear: () -> Unit,
    onClearAll: () -> Unit,
) {
    var hasFocus by remember { mutableStateOf(false) }

    val interactionSource = MutableInteractionSource()

    Box(
        modifier = modifier
            .background(
                color = Color.Transparent,
                shape = RectangleShape
            ).indication(interactionSource, LocalIndication.current)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        val press = PressInteraction.Press(offset)
                        interactionSource.emit(press)
                        hasFocus = true
                        tryAwaitRelease()
                        interactionSource.emit(PressInteraction.Release(press))
                        if (isBackSpace) onClear() else onKeyPress(text)
                        hasFocus = false
                    },
                    onLongPress = {
                        if (isBackSpace) onClearAll()
                    },
                    onTap = {}
                )
            }
            .border(
                width = 1.dp,
                color = if (hasFocus) MaterialTheme.colors.secondary else Color.Transparent,
                shape = RoundedCornerShape(5.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isBackSpace) {
            Icon(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                painter = painterResource(MR.images.clear),
                tint = if (hasFocus) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
                contentDescription = null
            )
            return
        }
        Text(
            text = text,
            color = if (hasFocus) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.body2
        )
    }
}