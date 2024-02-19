package com.dnapayments.mp.utils.chart.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.dnapayments.mp.presentation.theme.grey6

val LocalChartColors = staticCompositionLocalOf {
    ChartColors(
        primary = Color.Unspecified,
        surface = Color.Unspecified,
        grid = grey6,
        emptyGasBottle = Color.Unspecified,
        fullGasBottle = Color.Unspecified,
        overlayLine = Color.Unspecified,
    )
}

internal object ChartTheme {
    val colors: ChartColors
        @Composable
        get() = LocalChartColors.current
}
