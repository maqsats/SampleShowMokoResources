package com.dnapayments.mp.domain.model.pincode

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp

sealed interface Code {
    val isCorrect: Boolean
    val input: String

    data class Pin(
        override val isCorrect: Boolean = true,
        override val input: String = "",
    ) : Code

    data class Numeric(
        override val isCorrect: Boolean = true,
        override val input: String = "",
    ) : Code

    sealed interface Configuration {
        val colorScheme: ColorScheme

        class Pin(override val colorScheme: ColorScheme) : Configuration

        class Numeric(
            val textMeasurer: TextMeasurer,
            val textStyle: TextStyle,
            override val colorScheme: ColorScheme,
        ) : Configuration

        class ColorScheme(
            val filled: Color,
            val empty: Color,
            val error: Color,
        )
    }
}

private val radius = 7.dp

/* errorRadius must have different value of radius
in order to draw digit with error color otherwise
it won't work */
private val errorRadius = radius - 1.dp
private val borderWidth = 2.dp

// redraw a pin dot to a dot with different color
internal inline fun DrawScope.drawPinPattern(
    position: Int,
    value: Code.Pin,
    configurationProvider: () -> Code.Configuration.Pin,
) {
    val configuration = configurationProvider()
    val colorScheme = configuration.colorScheme

    when {
        !value.isCorrect -> drawCircle(color = colorScheme.error)
        position <= value.input.length -> drawCircle(color = colorScheme.filled)
        else -> drawEmptyCircle(color = colorScheme.empty)
    }
}

//redraw a pin dot to an entered text
internal inline fun DrawScope.drawNumericPattern(
    position: Int,
    value: Code.Numeric,
    configurationProvider: () -> Code.Configuration.Numeric,
) {
    val configuration = configurationProvider()
    val colorScheme = configuration.colorScheme

    when {
        position <= value.input.length -> {
            with(value) {
                /* textMeasurer is used for good representation of a text for different pixels density
                * topLeft is used in order to get the digit in full size (non-cut)*/
                drawText(
                    textMeasurer = configuration.textMeasurer,
                    text = input[position - 1].toString(),
                    topLeft = Offset(
                        y = if (isCorrect) -radius.toPx() else -errorRadius.toPx(),
                        x = 0F
                    ),
                    style = configuration.textStyle.copy(
                        color = if (isCorrect) colorScheme.filled else colorScheme.error
                    )
                )
            }
        }

        else -> drawEmptyCircle(color = colorScheme.empty)
    }
}

private fun DrawScope.drawEmptyCircle(color: Color) =
    drawCircle(
        color = color,
        radius = radius.toPx(),
        style = Stroke(width = borderWidth.toPx())
    )
