package daniel.avila.rnm.kmm.presentation.ui.features.line_chart

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soywiz.klock.DateTime
import com.soywiz.klock.TimeSpan
import daniel.avila.rnm.kmm.utils.chart.ChartAnimation
import daniel.avila.rnm.kmm.utils.chart.line.LineChart
import daniel.avila.rnm.kmm.utils.chart.line.LineChartData
import daniel.avila.rnm.kmm.utils.chart.line.LineChartPoint
import daniel.avila.rnm.kmm.utils.chart.line.LineChartSeries

@Composable
fun LineChartPreview() {
    val lineData = remember {
        LineChartData(
            series = listOf(LineChartSeries(
                dataName = "data ${DateTime.now().format("yyyy-MM-dd")}",
                lineColor = Color.Red,
                listOfPoints = (1..10).map { point ->
                    LineChartPoint(
                        x = DateTime.now()
                            .minus(TimeSpan(point * 24 * 60 * 60 * 1000.0)).unixMillisLong,
                        y = (468..490).random().toFloat(),
                    )
                }
            )),
        )
    }

    LineChart(
        lineChartData = lineData,
        modifier = Modifier.height(300.dp).padding(15.dp),
        xAxisLabel = {
            Text(
                fontSize = 9.sp,
                text = DateTime.fromUnix(it as Long).format("dd MMM"),
                textAlign = TextAlign.End,
                modifier = Modifier.padding(start = 20.dp)
            )
        },
        yAxisLabel = {
            Text(
                fontSize = 12.sp,
                text = it.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        },
        overlayHeaderLabel = {
            Text(
                text = DateTime.fromUnix(it as Long).format("dd MMM"),
                style = MaterialTheme.typography.overline
            )
        },
        overlayDataEntryLabel = { dataName, value ->
            Text(
                text = "$value"
            )
        },
        maxHorizontalLines = 7,
        maxVerticalLines = 5,

        animation = ChartAnimation.Sequenced()
    )
}

@Composable
private fun getLineChartSampleData(): LineChartData {
    val startTime = 15L
    val list = mutableListOf<LineChartSeries>()
    val hoursInMillis = 23L
    list.add(
        LineChartSeries(
            "",
            lineColor = Color.Red,
            dashedLine = false,
            listOfPoints = listOf(
                LineChartPoint(1L * hoursInMillis + startTime, 465.5f),
                LineChartPoint(2L * hoursInMillis + startTime, 465.2f),
                LineChartPoint(3L * hoursInMillis + startTime, 465.8f),
                LineChartPoint(4L * hoursInMillis + startTime, 468.3f),
                LineChartPoint(3L * hoursInMillis + startTime, 465.2f),
            )
        )
    )

    return LineChartData(
        series = list,
    )
}