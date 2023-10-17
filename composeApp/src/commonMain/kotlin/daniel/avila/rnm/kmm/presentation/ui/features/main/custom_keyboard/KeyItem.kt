package daniel.avila.rnm.kmm.presentation.ui.features.main.custom_keyboard

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun KeyItem(modifier: Modifier = Modifier, text: String, onKeyPress: (String) -> Unit) {
    var hasFocus by remember { mutableStateOf(false) }

    // Define animation specs for text color change
    val textColorAnimationSpec = remember {
        TweenSpec<Color>(
            durationMillis = 100,
            easing = FastOutSlowInEasing
        )
    }

    val borderColor by animateColorAsState(
        targetValue = if (hasFocus) MaterialTheme.colors.secondary else Color.Transparent,
        animationSpec = textColorAnimationSpec
    )

    val textColor by animateColorAsState(
        targetValue = if (hasFocus) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
        animationSpec = textColorAnimationSpec
    )


    val scope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .background(
                color = Color.Transparent,
                shape = RectangleShape
            ).clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {
                    scope.launch {
                        hasFocus = true
                        delay(100)
                        onKeyPress(text)
                        delay(50)
                        hasFocus = false
                    }
                }
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(5.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.body2
        )
    }
}