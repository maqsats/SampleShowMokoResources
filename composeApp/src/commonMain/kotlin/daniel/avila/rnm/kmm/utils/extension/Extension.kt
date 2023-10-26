package daniel.avila.rnm.kmm.utils.extension

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.shimmerLoadingAnimation(): Modifier {
    return composed {

        val shimmerColors = listOf(
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 1.0f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 0.3f),
        )

        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = 100f, y = 0.0f),
                end = Offset(x = 400f, y = 270f),
            ),
        )
    }
}

fun Double.formatMoney(inputText: String): String {
    if (inputText.isEmpty()) return ""

    var multiplier = inputText.toIntOrNull() ?: 1

    if (multiplier == 0) multiplier = 1

    println(multiplier)
    val scaledValue = this * multiplier
    val intValue = scaledValue.toLong()
    val decimalValue = ((scaledValue - intValue) * 100).toInt()

    val integralPart = addCommas(intValue)
    val decimalPart = decimalValue.toString().padStart(2, '0')

    return "$integralPart,$decimalPart"
}

fun addCommas(number: Long): String {
    val str = number.toString()
    val result = StringBuilder()

    for (i in str.indices.reversed()) {
        result.insert(0, str[i])
        if (i > 0 && (str.length - i) % 3 == 0) {
            result.insert(0, ' ')
        }
    }

    return result.toString()
}

fun Double.formatDistance(): String {
    return when {
        this < 1_000 -> "${this.toInt()} m"
        else -> {
            val kmValue = this / 1_000
            val formattedKm = if (kmValue % 1 == 0.0) {
                kmValue.toInt().toString()
            } else {
                "${(kmValue * 10).toInt() / 10.0}".replace(".", ",")
            }
            "$formattedKm km"
        }
    }
}