@file:Suppress("UNCHECKED_CAST")

package com.dna.payments.kmm.presentation.ui.features.overview.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.overview.OverviewType
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentStatus
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentSummary
import com.dna.payments.kmm.domain.model.status_summary.Summary
import com.dna.payments.kmm.domain.model.text_switch.DnaTextSwitchType
import com.dna.payments.kmm.domain.model.text_switch.TextSwitch
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
import com.dna.payments.kmm.presentation.ui.common.DnaTextSwitch
import com.dna.payments.kmm.presentation.ui.features.overview.OverviewContract
import com.dna.payments.kmm.utils.extension.toMoneyString

@Composable
fun ApprovalRateWidget(state: OverviewContract.State, overviewType: OverviewType) {

    var selectedTabIndex by remember {
        mutableStateOf(DnaTextSwitchType.AMOUNT.index)
    }

    Column(
        modifier = Modifier.padding(Paddings.medium)
    ) {

        Row {
            Spacer(modifier = Modifier.weight(1f))

            DnaTextSwitch(
                selectedIndex = selectedTabIndex,
                items = DnaTextSwitchType.values() as Array<TextSwitch>,
                onSelectionChange = { index ->
                    selectedTabIndex = index
                }
            )
        }

        Spacer(modifier = Modifier.height(Paddings.small))

        when (overviewType) {
            OverviewType.POS_PAYMENTS -> {
                PosPaymentsWidget(
                    posPaymentsSummaryList = state.posPaymentsSummaryList,
                    currency = state.selectedCurrency,
                    dnaTextSwitchType = DnaTextSwitchType.values()[selectedTabIndex]
                )
            }
            OverviewType.ONLINE_PAYMENTS -> {
                OnlinePaymentsWidget(
                    onlinePaymentsSummaryList = state.onlinePaymentsSummaryList,
                    currency = state.selectedCurrency,
                    dnaTextSwitchType = DnaTextSwitchType.values()[selectedTabIndex]
                )
            }
        }
    }
}


@Composable
fun PosPaymentsWidget(
    posPaymentsSummaryList: ResourceUiState<List<PosPaymentSummary>>,
    dnaTextSwitchType: DnaTextSwitchType,
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
                dnaTextSwitchType = dnaTextSwitchType,
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
    dnaTextSwitchType: DnaTextSwitchType,
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
                dnaTextSwitchType = dnaTextSwitchType,
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
    dnaTextSwitchType: DnaTextSwitchType,
    currency: Currency
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            Paddings.medium
        )
    ) {
        onlinePaymentsSummaryList.forEach { summary ->
            Row {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    DNAText(
                        text = summary.status.value,
                        style = DnaTextStyle.Medium12Grey5
                    )
                    DNAText(
                        text = when (dnaTextSwitchType) {
                            DnaTextSwitchType.AMOUNT -> summary.amount.toMoneyString(currency.name)
                            DnaTextSwitchType.COUNT -> summary.count.toString()
                        },
                        style = DnaTextStyle.SemiBold20
                    )
                    Spacer(modifier = Modifier.padding(Paddings.small))
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
    dnaTextSwitchType: DnaTextSwitchType,
    currency: Currency
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            Paddings.medium
        )
    ) {
        posPaymentsSummaryList.forEach { summary ->
            Row {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    DNAText(
                        text = summary.status.displayName,
                        style = DnaTextStyle.Medium12Grey5
                    )
                    DNAText(
                        text = when (dnaTextSwitchType) {
                            DnaTextSwitchType.AMOUNT -> summary.amount.toMoneyString(currency.name)
                            DnaTextSwitchType.COUNT -> summary.count.toString()
                        },
                        style = DnaTextStyle.SemiBold20
                    )
                    Spacer(modifier = Modifier.padding(Paddings.small))
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