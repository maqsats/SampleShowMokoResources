package daniel.avila.rnm.kmm.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.utils.extension.shimmerLoadingAnimation

@Composable
fun LoadingView(
    cornerBorderSize: Dp = 8.dp,
    height: Dp = 30.dp,
    width: Dp = 100.dp,
    backgroundColor: Color = MaterialTheme.colors.surface.copy(alpha = 0.2f)
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(cornerBorderSize))
            .background(color = backgroundColor)
            .size(height = height, width = width)
            .shimmerLoadingAnimation()
    )
}