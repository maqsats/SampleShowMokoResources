package com.dnapayments.mp.utils.pull_to_refresh

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dnapayments.mp.MR
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun PullToRefreshIndicator(
    state: PullToRefreshState,
    refreshTriggerDistance: Dp,
    refreshingOffset: Dp,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default.copy(color = Color.Gray, fontSize = 14.sp),
) {
    val refreshTriggerPx = with(LocalDensity.current) { refreshTriggerDistance.toPx() }
    val refreshingOffsetPx = with(LocalDensity.current) { refreshingOffset.toPx() }
    val indicatorHeight = 48.dp
    val indicatorHeightPx = with(LocalDensity.current) { indicatorHeight.toPx() }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(indicatorHeight)
            .padding(end = 26.dp)
            .graphicsLayer {
                translationY = state.contentOffset - (refreshingOffsetPx + indicatorHeightPx) / 2
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (state.isRefreshing) {
            val transition = rememberInfiniteTransition()
            val progress by transition.animateValue(
                0f,
                1f,
                Float.VectorConverter,
                infiniteRepeatable(
                    animation = tween(
                        durationMillis = 1332, // 1 and 1/3 second
                        easing = LinearEasing
                    )
                )
            )
            Image(
                painter = painterResource(MR.images.refresh),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
                    .rotate(progress * 360),
                colorFilter = ColorFilter.tint(textStyle.color)
            )
        } else {
            val progress = ((state.contentOffset - refreshTriggerPx / 2) / refreshTriggerPx * 2)
                .coerceIn(0f, 1f)
            Image(
                painter = painterResource(MR.images.refresh_arrow),
                contentDescription = "pull to refresh",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
                    .rotate(progress * 180),
                colorFilter = ColorFilter.tint(textStyle.color)
            )
        }
        BasicText(
            text = stringResource(
                when {
                    state.isPullInProgress && state.contentOffset >= refreshTriggerPx -> MR.strings.cptr_release_to_refresh
                    state.isRefreshing -> MR.strings.cptr_refreshing
                    else -> MR.strings.cptr_pull_to_refresh
                }
            ),
            style = textStyle
        )
    }
}
