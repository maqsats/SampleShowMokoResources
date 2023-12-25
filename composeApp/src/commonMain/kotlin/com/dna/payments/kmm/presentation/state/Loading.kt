package com.dna.payments.kmm.presentation.state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.presentation.theme.grayLight
import com.dna.payments.kmm.presentation.theme.greyColorAlpha
import com.dna.payments.kmm.utils.extension.shimmerLoadingAnimation

@Composable
fun Loading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = greyColorAlpha)
            .height(250.dp)
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentSquare(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(color = grayLight)
            .size(100.dp)
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentRectangle(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(color = grayLight)
            .height(200.dp)
            .fillMaxWidth()
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentRectangleLineLong(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = grayLight)
            .height(20.dp).fillMaxWidth()
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentRectangleLineShort(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = grayLight)
            .height(20.dp).fillMaxWidth(0.5f)
            .shimmerLoadingAnimation()
    )
}