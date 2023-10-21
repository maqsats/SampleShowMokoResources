package daniel.avila.rnm.kmm.presentation.ui.features.main.custom_keyboard

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.MR
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
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
            ).combinedClickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {
                    scope.launch {
                        hasFocus = true
                        delay(100)
                        if (isBackSpace) onClear() else onKeyPress(text)
                        delay(50)
                        hasFocus = false
                    }
                },
                onLongClick = {
                    scope.launch {
                        hasFocus = true
                        delay(100)
                        onClearAll()
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
        if (isBackSpace) {
            Icon(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                painter = painterResource(MR.images.clear),
                tint = textColor,
                contentDescription = null
            )
            return
        }
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.body2
        )
    }
}