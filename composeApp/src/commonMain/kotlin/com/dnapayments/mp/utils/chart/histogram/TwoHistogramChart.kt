package com.dnapayments.mp.utils.chart.histogram

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.dnaGreenLight
import com.dnapayments.mp.presentation.theme.dnaYellow
import com.dnapayments.mp.presentation.theme.grey6
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.utils.extension.addSpace
import com.dnapayments.mp.utils.extension.toMoneyString

@Composable
fun <T : Number, V> TwoHistogramChart(
    xFirstPoints: List<V>,
    xSecondPoints: List<V>,
    yFirstPoints: List<T>,
    ySecondPoints: List<T>,
) {
    require(xFirstPoints.size == yFirstPoints.size) { "xPoints and yPoints must have the same size" }
    require(xSecondPoints.size == ySecondPoints.size) { "xPoints and yPoints must have the same size" }

    val yMaxFirst = yFirstPoints.maxOf { it.toDouble() }.coerceAtLeast(0.0)
    val yMaxSecond = ySecondPoints.maxOf { it.toDouble() }.coerceAtLeast(0.0)

    val yMax = maxOf(yMaxFirst, yMaxSecond)

    var yCurrent = yMax

    val chartHeight = 6

    val yDifference = yMax / chartHeight


    val diff = Paddings.small

    val outlineColor = grey6

    val boxHeight = 200.dp

    val yPercent = yMax / 100.0

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(boxHeight)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(diff),
            horizontalAlignment = Alignment.Start,
        ) {
            repeat(chartHeight) {
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
                        text = yCurrent.toMoneyString().addSpace(),
                        style = DnaTextStyle.Normal10Grey5,
                        textAlign = TextAlign.End,
                        modifier = Modifier.defaultMinSize(35.dp)
                    )
                    yCurrent -= yDifference
                }
            }
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
                    text = "0".addSpace(),
                    style = DnaTextStyle.Normal10Grey5,
                    textAlign = TextAlign.End,
                    modifier = Modifier.defaultMinSize(35.dp)
                )
                yCurrent = 0.0
            }
            Spacer(modifier = Modifier.height(Paddings.medium))
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
            items(xFirstPoints.size) { key ->
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = diff)
                        .drawBehind {
                            val firstChartValue = yFirstPoints.getValueAtIndexOrReturnDefault(key)
                            val secondChartValue = ySecondPoints.getValueAtIndexOrReturnDefault(key)

                            when {
                                firstChartValue > 0.5 && secondChartValue > 0.5 -> {
                                    when {
                                        firstChartValue > secondChartValue -> {
                                            calculateHeightAndDrawRect(
                                                chartValue = firstChartValue,
                                                diff = diff,
                                                yPercent = yPercent,
                                                color = dnaGreenLight
                                            )
                                            calculateHeightAndDrawRect(
                                                chartValue = secondChartValue,
                                                diff = diff,
                                                yPercent = yPercent,
                                                color = dnaYellow
                                            )
                                        }
                                        else -> {
                                            calculateHeightAndDrawRect(
                                                chartValue = secondChartValue,
                                                diff = diff,
                                                yPercent = yPercent,
                                                color = dnaYellow
                                            )
                                            calculateHeightAndDrawRect(
                                                chartValue = firstChartValue,
                                                diff = diff,
                                                yPercent = yPercent,
                                                color = dnaGreenLight
                                            )
                                        }
                                    }
                                }
                                firstChartValue > 0.5 -> {
                                    calculateHeightAndDrawRect(
                                        firstChartValue,
                                        diff,
                                        yPercent,
                                        dnaGreenLight
                                    )
                                }
                                secondChartValue > 0.5 -> {
                                    calculateHeightAndDrawRect(
                                        secondChartValue,
                                        diff,
                                        yPercent,
                                        dnaYellow
                                    )
                                }
                            }
                        },
                    verticalArrangement = Arrangement.Bottom
                ) {
                    DNAText(
                        text = xFirstPoints[key].toString(),
                        style = DnaTextStyle.Normal10Grey5
                    )
                }
            }
        }
    }
}