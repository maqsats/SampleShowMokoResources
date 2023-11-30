package com.dna.payments.kmm.presentation.state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.presentation.theme.greyColorAlpha
import com.dna.payments.kmm.utils.extension.shimmerLoadingAnimation
import com.valentinilk.shimmer.shimmer

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