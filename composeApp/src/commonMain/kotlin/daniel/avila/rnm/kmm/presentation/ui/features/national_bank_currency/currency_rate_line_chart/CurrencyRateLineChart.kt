package daniel.avila.rnm.kmm.presentation.ui.features.national_bank_currency.currency_rate_line_chart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import com.soywiz.klock.parse
import daniel.avila.rnm.kmm.domain.model.time_period_tab.TimePeriod
import daniel.avila.rnm.kmm.presentation.state.ManagementResourceUiState
import daniel.avila.rnm.kmm.presentation.ui.features.national_bank_currency.NBCurrencyContract
import daniel.avila.rnm.kmm.presentation.ui.features.national_bank_currency.NBCurrencyViewModel
import daniel.avila.rnm.kmm.utils.chart.ChartAnimation
import daniel.avila.rnm.kmm.utils.chart.line.LineChart
import daniel.avila.rnm.kmm.utils.chart.line.LineChartData
import daniel.avila.rnm.kmm.utils.chart.line.LineChartPoint
import daniel.avila.rnm.kmm.utils.chart.line.LineChartSeries
import org.koin.compose.koinInject

@Composable
fun CurrencyRateLineChart(timePeriod: TimePeriod, currencyCode: String) {


    val currencyViewModel = koinInject<NBCurrencyViewModel>()

    val state by currencyViewModel.uiState.collectAsState()

    LaunchedEffect(timePeriod) {
        currencyViewModel.setEvent(
            NBCurrencyContract.Event.OnFetchData(
                Pair(
                    timePeriod,
                    currencyCode
                )
            )
        )
    }

    ManagementResourceUiState(
        modifier = Modifier.height(300.dp).padding(15.dp),
        resourceUiState = state.nationalBankCurrencyList,
        successView = { currencyList ->

            val lineData = remember {
                LineChartData(
                    series = listOf(LineChartSeries(
                        dataName = "",
                        lineColor = Color.Red,
                        listOfPoints = currencyList.map { currencyItem ->
                            LineChartPoint(
                                x = DateFormat("yyyy-MM-dd").parse(currencyItem.date).local.unixMillisLong,
                                y = currencyItem.rate.toFloat(),
                            )
                        }.sortedBy {
                            it.x
                        }
                    )),
                )
            }

            LineChart(
                modifier = Modifier.fillMaxSize(),
                lineChartData = lineData,
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
        },
        onTryAgain = { currencyViewModel.setEvent(NBCurrencyContract.Event.OnTryCheckAgainClick) },
        onCheckAgain = { currencyViewModel.setEvent(NBCurrencyContract.Event.OnTryCheckAgainClick) },
    )
}