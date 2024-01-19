@file:Suppress("UNCHECKED_CAST")

package com.dna.payments.kmm.presentation.ui.features.overview.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.domain.interactors.use_cases.currency.CurrencyHelper
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.reports.HistogramEntry
import com.dna.payments.kmm.domain.model.text_switch.DnaTextSwitchType
import com.dna.payments.kmm.domain.model.text_switch.TextSwitch
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.state.ComponentRectangleLineLong
import com.dna.payments.kmm.presentation.state.ComponentRectangleVerticalLineLong
import com.dna.payments.kmm.presentation.state.ManagementResourceUiState
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DnaTextSwitch
import com.dna.payments.kmm.utils.chart.histogram.HistogramChart
import com.dna.payments.kmm.utils.chart.histogram.TwoHistogramChart

@Composable
fun ChargedTransactionsWidget(
    onlinePaymentsGraphSummary: ResourceUiState<Pair<HistogramEntry, HistogramEntry>>,
    selectedCurrency: Currency,
    showComparison: Boolean = false
) {

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

        ManagementResourceUiState(
            modifier = Modifier.fillMaxWidth(),
            resourceUiState = onlinePaymentsGraphSummary,
            onCheckAgain = {},
            onTryAgain = {},
            successView = { responsePair ->
                if (showComparison) {
                    TwoHistogramChart(
                        currency = if (selectedTabIndex == DnaTextSwitchType.AMOUNT.index) CurrencyHelper(
                            selectedCurrency.name
                        ) else "",
                        xFirstPoints = responsePair.first.labelList,
                        yFirstPoints = when (selectedTabIndex) {
                            DnaTextSwitchType.AMOUNT.index -> responsePair.first.amountList.map { barEntry ->
                                barEntry.x
                            }
                            else -> responsePair.first.countList.map { barEntry ->
                                barEntry.x
                            }
                        },
                        xSecondPoints = responsePair.second.labelList,
                        ySecondPoints = when (selectedTabIndex) {
                            DnaTextSwitchType.AMOUNT.index -> responsePair.second.amountList.map { barEntry ->
                                barEntry.x
                            }
                            else -> responsePair.second.countList.map { barEntry ->
                                barEntry.x
                            }
                        }
                    )
                } else {
                    HistogramChart(
                        currency = if (selectedTabIndex == DnaTextSwitchType.AMOUNT.index) CurrencyHelper(
                            selectedCurrency.name
                        ) else "",
                        xPoints = responsePair.first.labelList,
                        yPoints =
                        when (selectedTabIndex) {
                            DnaTextSwitchType.AMOUNT.index -> responsePair.first.amountList.map { barEntry ->
                                barEntry.x
                            }
                            else -> responsePair.first.countList.map { barEntry ->
                                barEntry.x
                            }
                        }
                    )
                }
            },
            loadingView = {
                HistogramLoading()
            },
            msgCheckAgain = {}
        )
    }
}

@Composable
fun HistogramLoading() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(Paddings.medium),
            horizontalArrangement = Arrangement.spacedBy(Paddings.medium)
        ) {
            repeat(10) {
                ComponentRectangleVerticalLineLong()
            }
        }

        Spacer(modifier = Modifier.height(Paddings.medium))

        ComponentRectangleLineLong()
    }
}