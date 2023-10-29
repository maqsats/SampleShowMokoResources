package daniel.avila.rnm.kmm.utils.chart.gasbottle

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import daniel.avila.rnm.kmm.utils.chart.theme.ChartColors

@Immutable
data class GasBottleColors(
    val fullGasBottle: Color,
    val emptyGasBottle: Color,
)

val ChartColors.gasBottleColors
    get() = GasBottleColors(
        fullGasBottle = fullGasBottle,
        emptyGasBottle = emptyGasBottle,
    )
