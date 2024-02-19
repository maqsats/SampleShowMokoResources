package com.dnapayments.mp.presentation.ui.features.overview_report.widgets.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dnapayments.mp.domain.model.currency.Currency
import com.dnapayments.mp.domain.model.date_picker.DateSelection
import com.dnapayments.mp.domain.model.date_picker.Menu
import com.dnapayments.mp.domain.model.overview_report.OverviewReportType
import com.dnapayments.mp.domain.model.text_switch.DnaOrderByType
import com.dnapayments.mp.presentation.state.ComponentRectangleLineLong
import com.dnapayments.mp.presentation.state.ComponentRectangleVerticalLineLong
import com.dnapayments.mp.presentation.state.ManagementResourceUiState
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.ui.common.DnaFilter
import com.dnapayments.mp.presentation.ui.common.DnaTextSwitch
import com.dnapayments.mp.presentation.ui.features.overview_report.widgets.payment_status_bottom_sheet.PaymentStatusBottomSheet
import com.dnapayments.mp.presentation.ui.features.overview_report.widgets.payment_status_bottom_sheet.PaymentStatusDropDownContent
import com.dnapayments.mp.utils.chart.histogram.HistogramChart
import com.dnapayments.mp.utils.chart.histogram.TwoHistogramChart
import org.koin.core.parameter.parametersOf

class TransactionsWidget(
    private val overviewReportType: OverviewReportType,
    private val dateSelection: DateSelection,
    private val selectedCurrency: Currency,
    private val showComparison: Boolean = false,
    private val menuType: Menu,
) : Screen {
    override val key: ScreenKey
        get() = "TransactionsWidget${overviewReportType}$showComparison"

    @Composable
    override fun Content() {

        val viewModel = getScreenModel<TransactionsViewModel> {
            parametersOf(overviewReportType)
        }

        val state by viewModel.uiState.collectAsState()

        val openPaymentStatusListBottomSheet = rememberSaveable { mutableStateOf(false) }

        var selectedTabIndex by remember {
            mutableStateOf(DnaOrderByType.AMOUNT.index)
        }

        LaunchedEffect(
            key1 = dateSelection,
            key2 = selectedCurrency
        ) {
            viewModel.setEvent(
                TransactionsContract.Event.OnFetchData(
                    dateSelection = dateSelection,
                    currency = selectedCurrency
                )
            )
        }

        Column(
            modifier = Modifier.padding(Paddings.medium)
        ) {
            Row {
                if (menuType == Menu.REPORTS)
                    DnaFilter(
                        openBottomSheet = openPaymentStatusListBottomSheet,
                        dropDownContent = {
                            PaymentStatusDropDownContent(
                                selectedPaymentStatus = state.selectedPaymentStatus
                            )
                        },
                        bottomSheetContent = {
                            PaymentStatusBottomSheet(
                                paymentStatusList = state.paymentStatusList,
                                selectedPaymentStatus = state.selectedPaymentStatus
                            ) {
                                openPaymentStatusListBottomSheet.value = false
                                viewModel.setEvent(
                                    TransactionsContract.Event.OnPaymentStatusChange(
                                        paymentStatus = it
                                    )
                                )
                            }
                        }
                    )

                Spacer(modifier = Modifier.weight(1f))

                DnaTextSwitch(
                    selectedIndex = selectedTabIndex,
                    items = DnaOrderByType.entries.toTypedArray(),
                    onSelectionChange = { index ->
                        selectedTabIndex = index
                    }
                )
            }

            ManagementResourceUiState(
                modifier = Modifier.fillMaxWidth(),
                resourceUiState = state.paymentsGraphSummary,
                onCheckAgain = {},
                onTryAgain = {},
                successView = { responsePair ->
                    if (showComparison) {
                        TwoHistogramChart(
                            xFirstPoints = responsePair.first.labelList,
                            yFirstPoints = when (selectedTabIndex) {
                                DnaOrderByType.AMOUNT.index -> responsePair.first.amountList.map { barEntry ->
                                    barEntry.x
                                }
                                else -> responsePair.first.countList.map { barEntry ->
                                    barEntry.x
                                }
                            },
                            xSecondPoints = responsePair.second.labelList,
                            ySecondPoints = when (selectedTabIndex) {
                                DnaOrderByType.AMOUNT.index -> responsePair.second.amountList.map { barEntry ->
                                    barEntry.x
                                }
                                else -> responsePair.second.countList.map { barEntry ->
                                    barEntry.x
                                }
                            }
                        )
                    } else {
                        HistogramChart(
                            xPoints = responsePair.first.labelList,
                            yPoints =
                            when (selectedTabIndex) {
                                DnaOrderByType.AMOUNT.index -> responsePair.first.amountList.map { barEntry ->
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
                    TransactionWidgetLoading()
                },
                msgCheckAgain = {}
            )
        }
    }
}


@Composable
fun TransactionWidgetLoading() {
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