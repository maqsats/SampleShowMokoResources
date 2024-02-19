package com.dnapayments.mp.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.currency.Currency
import com.dnapayments.mp.presentation.state.ComponentRectangleLineLong
import com.dnapayments.mp.presentation.state.ComponentRectangleLineShort
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.greyColorBackground
import com.dnapayments.mp.presentation.ui.common.DnaChartConstants.DNA_CHART_HEIGHT
import com.dnapayments.mp.presentation.ui.common.DnaChartConstants.DNA_CHART_HEIGHT_BIGGER
import com.dnapayments.mp.presentation.ui.common.DnaChartConstants.DNA_GAP
import com.dnapayments.mp.presentation.ui.common.DnaChartConstants.DNA_PIE_LOADING_SIZE
import com.dnapayments.mp.presentation.ui.common.DnaChartConstants.DNA_THICKNESS
import com.dnapayments.mp.utils.chart.ChartAnimation
import com.dnapayments.mp.utils.chart.mapValueToDifferentRange
import com.dnapayments.mp.utils.chart.pie.LegendIcon
import com.dnapayments.mp.utils.chart.pie.LegendOrientation
import com.dnapayments.mp.utils.chart.pie.PieChartConfig
import com.dnapayments.mp.utils.chart.pie.PieChartData
import com.dnapayments.mp.utils.chart.pie.PieChartWithLegend
import com.dnapayments.mp.utils.chart.pie.PieDefaults.FULL_CIRCLE_DEGREES
import com.dnapayments.mp.utils.chart.pie.PieDefaults.START_ANGLE
import com.dnapayments.mp.utils.chart.pie.drawArc
import com.dnapayments.mp.utils.extension.shimmerLoadingAnimation
import com.dnapayments.mp.utils.extension.toMoneyString
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun DnaChartWidget(
    pieChartList: List<PieChartData>,
    currency: Currency? = null,
) {
    PieChartWithLegend(
        pieChartList,
        modifier = Modifier
            .fillMaxWidth()
            .height(if (pieChartList.size < 3) DNA_CHART_HEIGHT else DNA_CHART_HEIGHT_BIGGER),
        animation = ChartAnimation.Simple(),
        config = PieChartConfig(
            thickness = DNA_THICKNESS,
            gap = DNA_GAP,
            legendIcon = LegendIcon.SQUARE,
            legendOrientation = LegendOrientation.VERTICAL,
        ),
        centerContent = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                DNAText(
                    text = stringResource(
                        when (currency) {
                            null -> MR.strings.volume_count
                            else -> MR.strings.volume
                        }
                    ),
                    style = DnaTextStyle.Medium14Grey4
                )
                DNAText(
                    text = when (currency) {
                        null -> pieChartList.sumOf { it.value }.toInt()
                            .toString()
                        else -> pieChartList.sumOf { it.value }
                            .toMoneyString(currency.name)
                    },
                    style = DnaTextStyle.SemiBold20
                )
            }
        },
        legendItemLabel = {
            if (it.value == 0.0) {
                return@PieChartWithLegend
            }
            Row(
                modifier = Modifier.padding(Paddings.extraSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DNAText(
                    it.name,
                    style = DnaTextStyle.Medium12
                )
            }
        }
    )
}

@Composable
fun DnaChartLoading() {
    Column {
        Spacer(modifier = Modifier.height(Paddings.medium))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(DNA_PIE_LOADING_SIZE)
                    .drawBehind {
                        drawArc(
                            color = greyColorBackground,
                            startAngle = START_ANGLE.toFloat(),
                            sweepAngle = 1.0.mapValueToDifferentRange(
                                inMin = 0.0,
                                inMax = 1.0,
                                outMin = 0.0,
                                outMax = FULL_CIRCLE_DEGREES.toDouble()
                            ).toFloat(),
                            config = PieChartConfig(
                                thickness = DNA_THICKNESS
                            ),
                        )
                    }.shimmerLoadingAnimation()
            )
        }
        Spacer(modifier = Modifier.height(Paddings.medium))
        ComponentRectangleLineLong()
        Spacer(modifier = Modifier.height(Paddings.extraSmall))
        ComponentRectangleLineShort()
        Spacer(modifier = Modifier.height(Paddings.extraSmall))
        ComponentRectangleLineLong()
        Spacer(modifier = Modifier.height(Paddings.extraSmall))
        ComponentRectangleLineShort()
    }
}

private object DnaChartConstants {
    val DNA_CHART_HEIGHT = 350.dp
    val DNA_PIE_LOADING_SIZE = 350.dp
    val DNA_CHART_HEIGHT_BIGGER = 450.dp
    val DNA_THICKNESS = 45.dp
    val DNA_GAP = 3.dp
}