package com.dnapayments.mp.utils.chart.histogram

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dnapayments.mp.presentation.theme.Paddings

fun DrawScope.calculateHeightAndDrawRect(
    chartValue: Double,
    diff: Dp,
    yPercent: Double,
    color: Color
) {
    val height = this.size.height - Paddings.standard12dp.toPx()
    val chHeight = height - (45.dp + diff).toPx()
    val chPercent = chHeight / 100f

    val rectHeight =
        chHeight - ((chartValue / yPercent) * chPercent).toFloat()

    val finalRectHeight =
        if (chHeight - rectHeight < 0) 0f else chHeight - rectHeight + Paddings.extraSmall.toPx()

    val chartTopLeftY =
        rectHeight + Paddings.large.toPx() + Paddings.small6dp.toPx()

    drawRect(
        color = color,
        topLeft = Offset(
            x = 0f,
            y = chartTopLeftY
        ),
        size = Size(
            width = Paddings.normal.toPx(),
            height = finalRectHeight
        )
    )
}

fun List<Number>.getValueAtIndexOrReturnDefault(index: Int): Double {
    return if (this.size > index) {
        this[index].toDouble()
    } else {
        0.0
    }
}