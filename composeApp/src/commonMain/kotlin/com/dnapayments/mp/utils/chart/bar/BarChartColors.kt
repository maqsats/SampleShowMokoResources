package com.dnapayments.mp.utils.chart.bar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.dnapayments.mp.utils.chart.theme.ChartColors

@Immutable
data class BarChartColors(
    val grid: Color,
    val surface: Color,
)

val ChartColors.barChartColors
    get() = BarChartColors(
        grid = grid,
        surface = surface,
    )
