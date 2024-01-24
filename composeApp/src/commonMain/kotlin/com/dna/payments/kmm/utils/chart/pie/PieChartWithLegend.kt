package com.dna.payments.kmm.utils.chart.pie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.utils.chart.ChartAnimation

/**
 * Version of [PieChart] with legend.
 *
 * @param legendItemLabel Composable to use to represent the item in the legend
 *
 * @see PieChart
 */
@Composable
fun PieChartWithLegend(
    pieChartData: List<PieChartData>,
    modifier: Modifier = Modifier,
    animation: ChartAnimation = ChartAnimation.Simple(),
    config: PieChartConfig = PieChartConfig(),
    legendItemLabel: @Composable (PieChartData) -> Unit = PieDefaults.LegendItemLabel,
    centerContent: @Composable BoxScope.() -> Unit = {},
) {
    when (config.legendOrientation) {
        LegendOrientation.VERTICAL ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(config.legendPadding),
                modifier = modifier.padding(top = Paddings.medium),
            ) {
                PieChart(
                    data = pieChartData,
                    modifier = Modifier.weight(1f),
                    animation = animation,
                    config = config,
                    centerContent = centerContent
                )
                PieChartLegend(
                    data = pieChartData,
                    animation = animation,
                    legendItemLabel = legendItemLabel,
                    config = config,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        LegendOrientation.HORIZONTAL ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(config.legendPadding),
                modifier = modifier,
            ) {
                PieChart(
                    data = pieChartData,
                    modifier = Modifier.fillMaxHeight(),
                    animation = animation,
                    config = config,
                )
                PieChartLegend(
                    data = pieChartData,
                    animation = animation,
                    legendItemLabel = legendItemLabel,
                    config = config,
                    modifier = Modifier.wrapContentWidth(),
                )
            }
    }
}
