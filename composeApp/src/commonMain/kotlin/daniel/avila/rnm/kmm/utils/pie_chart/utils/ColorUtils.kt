package daniel.avila.rnm.kmm.utils.pie_chart.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun Color.toLegacyInt(): Int {
    return this.toArgb()
}