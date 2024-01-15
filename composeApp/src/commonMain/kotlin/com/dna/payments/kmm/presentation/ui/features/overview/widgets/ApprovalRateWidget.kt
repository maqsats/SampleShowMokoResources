package com.dna.payments.kmm.presentation.ui.features.overview.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.overview.OverviewType
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentStatus
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentSummary
import com.dna.payments.kmm.domain.model.status_summary.Summary
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.state.ComponentCircle
import com.dna.payments.kmm.presentation.state.ComponentRectangleLineLong
import com.dna.payments.kmm.presentation.state.ComponentRectangleLineShort
import com.dna.payments.kmm.presentation.state.ManagementResourceUiState
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.AnimatedCircularProgressIndicator
import com.dna.payments.kmm.presentation.ui.common.CircularIndicatorDiameter
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.utils.extension.toMoneyString
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ApprovalRateWidget(
    posPaymentsSummaryList: ResourceUiState<List<PosPaymentSummary>>,
    onlinePaymentsSummaryList: ResourceUiState<List<Summary>>,
    selectedCurrency: Currency,
    overviewType: OverviewType
) {
    Column(
        modifier = Modifier.padding(Paddings.medium)
    ) {
        when (overviewType) {
            OverviewType.POS_PAYMENTS -> {
                PosPaymentsWidget(
                    posPaymentsSummaryList = posPaymentsSummaryList,
                    currency = selectedCurrency
                )
            }
            OverviewType.ONLINE_PAYMENTS -> {
                OnlinePaymentsWidget(
                    onlinePaymentsSummaryList = onlinePaymentsSummaryList,
                    currency = selectedCurrency
                )
            }
        }
    }
}


@Composable
fun PosPaymentsWidget(
    posPaymentsSummaryList: ResourceUiState<List<PosPaymentSummary>>,
    currency: Currency
) {
    ManagementResourceUiState(
        modifier = Modifier.fillMaxWidth(),
        resourceUiState = posPaymentsSummaryList,
        onCheckAgain = {},
        onTryAgain = {},
        successView = {
            PosPaymentsWidgetSuccess(
                posPaymentsSummaryList = it,
                currency = currency
            )
        },
        loadingView = {
            ApprovalRateWidgetLoading(3)
        },
        msgCheckAgain = {}
    )
}


@Composable
fun OnlinePaymentsWidget(
    onlinePaymentsSummaryList: ResourceUiState<List<Summary>>,
    currency: Currency
) {
    ManagementResourceUiState(
        modifier = Modifier.fillMaxWidth(),
        resourceUiState = onlinePaymentsSummaryList,
        onCheckAgain = {},
        onTryAgain = {},
        successView = {
            OnlinePaymentsWidgetSuccess(
                onlinePaymentsSummaryList = it,
                currency = currency
            )
        },
        loadingView = {
            ApprovalRateWidgetLoading(4)
        },
        msgCheckAgain = {}
    )
}

@Composable
fun ApprovalRateWidgetLoading(
    count: Int
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            Paddings.medium
        )
    ) {
        repeat(count) {
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
fun OnlinePaymentsWidgetSuccess(
    onlinePaymentsSummaryList: List<Summary>,
    currency: Currency
) {
    Column {
        onlinePaymentsSummaryList.forEach { summary ->
            Row(
                modifier = Modifier.padding(
                    top = if (onlinePaymentsSummaryList.first() == summary)
                        Paddings.default else Paddings.medium
                )
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    DNAText(
                        text = summary.status.value,
                        style = DnaTextStyle.Medium12Grey5
                    )
                    DNAText(
                        summary.amount.toMoneyString(currency.name),
                        style = DnaTextStyle.SemiBold20
                    )
                    DNAText(
                        text = stringResource(MR.strings.transactions_holder, summary.count),
                        style = DnaTextStyle.Normal10Grey5
                    )
                }
                AnimatedCircularProgressIndicator(
                    currentValue = summary.percentage.toInt()
                )
            }
        }
    }
}

@Composable
fun PosPaymentsWidgetSuccess(
    posPaymentsSummaryList: List<PosPaymentSummary>,
    currency: Currency
) {
    Column {
        posPaymentsSummaryList.forEach { summary ->
            Row(
                modifier = Modifier.padding(
                    top = if (posPaymentsSummaryList.first() == summary)
                        Paddings.default else Paddings.medium
                )
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    DNAText(
                        text = summary.status.displayName,
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
                if (summary.status != PosPaymentStatus.ALL) {
                    AnimatedCircularProgressIndicator(
                        currentValue = summary.percentage.toInt()
                    )
                }
            }
        }
    }
}