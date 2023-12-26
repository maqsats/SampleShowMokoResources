package com.dna.payments.kmm.utils.chart.histogram

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.dnaGreenLight
import com.dna.payments.kmm.presentation.theme.grey6
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.utils.extension.toMoneyString

@Composable
fun <T : Number, V> HistogramChart(
    isCompact: Boolean,
    currency: String,
    xPoints: List<V>,
    yPoints: List<T>
) {

    val yMax = yPoints.maxOf { it.toDouble() }.coerceAtLeast(0.0)

    var yCurrent = yMax

    val chartHeight = if (isCompact) 6 else 8

    val yDifference = yMax / chartHeight


    val diff = if (isCompact) Paddings.small else Paddings.medium

    val outlineColor = grey6
    val primaryColor = dnaGreenLight
    val boxHeight = if (isCompact) 200.dp else 320.dp

    val yPercent = yMax / 100.0

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(boxHeight)
    ) {
        LazyColumn(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(diff),
            horizontalAlignment = Alignment.Start,
        ) {
            items(chartHeight) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawLine(
                                color = outlineColor,
                                strokeWidth = 2f,
                                start = Offset(x = 40.dp.toPx(), y = (this.size.height / 2)),
                                end = Offset(x = this.size.width, y = this.size.height / 2)
                            )
                        },
                    horizontalArrangement = Arrangement.Start
                ) {
                    DNAText(
                        text = "${yCurrent.toMoneyString()}$currency ",
                        style = DnaTextStyle.Normal10Grey5,
                        textAlign = TextAlign.End,
                        modifier = Modifier.defaultMinSize(35.dp)
                    )
                    yCurrent -= yDifference
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawLine(
                                color = outlineColor,
                                strokeWidth = 2f,
                                start = Offset(x = 40.dp.toPx(), y = (this.size.height / 2)),
                                end = Offset(x = this.size.width, y = this.size.height / 2)
                            )
                        },
                    horizontalArrangement = Arrangement.Start
                ) {
                    DNAText(
                        text = "0$currency ",
                        style = DnaTextStyle.Normal10Grey5,
                        textAlign = TextAlign.End,
                        modifier = Modifier.defaultMinSize(35.dp)
                    )
                    yCurrent = 0.0
                }
                Spacer(modifier = Modifier.height(Paddings.medium))
            }
        }
        LazyRow(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 40.dp)
                .fillMaxWidth()
                .height(boxHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            items(xPoints.size) { key ->
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = diff)
                        .drawBehind {
                            val height = this.size.height - Paddings.standard12dp.toPx()

                            val chHeight = height - (45.dp + diff).toPx()
                            val chPercent = chHeight / 100f

                            if (yPoints[key].toDouble() < 0.5)
                                return@drawBehind

                            val rectHeight =
                                chHeight - ((yPoints[key].toDouble() / yPercent) * chPercent).toFloat()

                            val finalRectHeight =
                                if (chHeight - rectHeight < 0) 0f else chHeight - rectHeight

                            drawRect(
                                color = primaryColor,
                                topLeft = Offset(
                                    x = 0f,
                                    y = rectHeight + Paddings.extraLarge.toPx() + Paddings.standard.toPx()
                                ),
                                size = Size(
                                    width = Paddings.normal.toPx(),
                                    height = finalRectHeight
                                ),
                            )
                        },
                    verticalArrangement = Arrangement.Bottom
                ) {
                    DNAText(
                        text = xPoints[key].toString(),
                        style = DnaTextStyle.Normal10Grey5
                    )
                }
            }
        }
    }
}