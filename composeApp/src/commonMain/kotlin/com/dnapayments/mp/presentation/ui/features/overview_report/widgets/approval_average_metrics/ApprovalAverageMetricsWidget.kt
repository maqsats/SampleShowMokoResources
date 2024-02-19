package com.dnapayments.mp.presentation.ui.features.overview_report.widgets.approval_average_metrics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.average_metrics.Metric
import com.dnapayments.mp.domain.model.currency.Currency
import com.dnapayments.mp.domain.model.date_picker.DateSelection
import com.dnapayments.mp.domain.model.online_payments.OnlinePaymentStatus
import com.dnapayments.mp.domain.model.overview_report.OverviewReportType
import com.dnapayments.mp.domain.model.overview_report.Summary
import com.dnapayments.mp.domain.model.pos_payments.PosPaymentStatus
import com.dnapayments.mp.presentation.state.ComponentCircle
import com.dnapayments.mp.presentation.state.ComponentRectangleLineLong
import com.dnapayments.mp.presentation.state.ComponentRectangleLineShort
import com.dnapayments.mp.presentation.state.ManagementResourceUiState
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.ui.common.AnimatedCircularProgressIndicator
import com.dnapayments.mp.presentation.ui.common.CircularIndicatorDiameter
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.utils.extension.toMoneyString
import dev.icerock.moko.resources.compose.stringResource
import org.koin.core.parameter.parametersOf

class ApprovalAverageMetricsWidget(
    private val overviewReportType: OverviewReportType,
    private val dateSelection: DateSelection,
    private val selectedCurrency: Currency,
    private val isApprovalRate: Boolean
) : Screen {

    override val key: String
        get() = "ApprovalAverageMetricsWidget${overviewReportType}"

    @Composable
    override fun Content() {

        val viewModel = getScreenModel<ApprovalAverageMetricsViewModel> {
            parametersOf(overviewReportType)
        }

        val state by viewModel.uiState.collectAsState()

        LaunchedEffect(
            key1 = dateSelection,
            key2 = selectedCurrency
        ) {
            viewModel.setEvent(
                ApprovalAverageMetricsContract.Event.OnFetchData(
                    dateSelection = dateSelection,
                    currency = selectedCurrency
                )
            )
        }

        ManagementResourceUiState(
            modifier = Modifier.fillMaxWidth(),
            resourceUiState = state.paymentsSummaryList,
            onCheckAgain = {},
            onTryAgain = {},
            successView = { successResponse ->
                if (isApprovalRate)
                    ApprovalRateWidgetSuccess(
                        paymentsSummary = successResponse.first,
                        currency = selectedCurrency
                    )
                else AverageMetricsWidgetSuccess(
                    metricList = successResponse.second,
                    currency = selectedCurrency
                )
            },
            loadingView = {
                if (isApprovalRate)
                    ApprovalRateWidgetLoading()
                else
                    AverageMetricsWidgetLoading()
            },
            msgCheckAgain = {}
        )

    }

    @Composable
    private fun ApprovalRateWidgetSuccess(
        paymentsSummary: List<Summary>,
        currency: Currency
    ) {
        Column(
            modifier = Modifier.padding(Paddings.medium)
        ) {
            paymentsSummary.forEach { summary ->
                Row(
                    modifier = Modifier.padding(
                        top = if (paymentsSummary.first() == summary)
                            Paddings.default else Paddings.medium
                    )
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        DNAText(
                            text = when (
                                summary.status
                            ) {
                                is PosPaymentStatus -> summary.status.displayName
                                is OnlinePaymentStatus -> summary.status.value
                                else -> ""
                            },
                            style = DnaTextStyle.Medium12Grey5
                        )
                        DNAText(
                            text = summary.amount.toMoneyString(currency.name),
                            style = DnaTextStyle.SemiBold20
                        )
                        DNAText(
                            text = stringResource(MR.strings.transactions_holder, summary.count),
                            style = DnaTextStyle.Normal10Grey5
                        )
                    }
                    if (summary.status is PosPaymentStatus && summary.status == PosPaymentStatus.ALL) {
                        return@Row
                    }
                    AnimatedCircularProgressIndicator(
                        currentValue = summary.percentage.toInt()
                    )
                }
            }
        }
    }

    @Composable
    private fun AverageMetricsWidgetSuccess(metricList: List<Metric>, currency: Currency) {
        Column(
            modifier = Modifier.padding(
                horizontal = Paddings.medium
            )
        ) {
            metricList.forEach { metric ->

                if (metric != metricList.first())
                    Divider(modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(Paddings.small))

                DNAText(
                    text = stringResource(metric.description.stringResource),
                    style = DnaTextStyle.Medium12Grey4
                )

                DNAText(
                    text = metric.amount.toMoneyString(currency.name),
                    style = DnaTextStyle.SemiBold16
                )

                Spacer(modifier = Modifier.height(Paddings.small))
            }
        }
    }

    @Composable
    private fun ApprovalRateWidgetLoading() {
        Column(
            modifier = Modifier.padding(Paddings.medium),
            verticalArrangement = Arrangement.spacedBy(
                Paddings.medium
            )
        ) {
            repeat(4) {
                Row {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        ComponentRectangleLineShort()
                        Spacer(modifier = Modifier.padding(Paddings.extraSmall))
                        ComponentRectangleLineLong()
                        Spacer(modifier = Modifier.padding(Paddings.small))
                    }
                    Spacer(modifier = Modifier.padding(Paddings.small))
                    ComponentCircle(modifier = Modifier.size(CircularIndicatorDiameter))
                }
            }
        }
    }

    @Composable
    private fun AverageMetricsWidgetLoading() {
        Column(
            modifier = Modifier.padding(
                horizontal = Paddings.medium
            )
        ) {
            repeat(3) {
                if (it != 0)
                    Divider(modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(Paddings.small))

                ComponentRectangleLineShort()

                Spacer(modifier = Modifier.height(Paddings.small))
            }
        }
    }
}