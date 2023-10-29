package daniel.avila.rnm.kmm.utils.chart.dial

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import daniel.avila.rnm.kmm.utils.chart.theme.ChartColors

@Immutable
data class DialColors(
    val progressBarColor: Color,
    val progressBarBackgroundColor: Color,
    val gridScaleColor: Color,
)

val ChartColors.dialColors
    get() = DialColors(
        progressBarColor = primary,
        progressBarBackgroundColor = grid,
        gridScaleColor = grid
    )
