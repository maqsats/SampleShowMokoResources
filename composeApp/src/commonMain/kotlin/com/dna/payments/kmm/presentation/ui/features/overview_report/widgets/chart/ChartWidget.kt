package com.dna.payments.kmm.presentation.ui.features.overview_report.widgets.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.domain.model.overview_report.OverviewReportType
import com.dna.payments.kmm.domain.model.overview_report.OverviewReportWidgetType
import com.dna.payments.kmm.domain.model.text_switch.DnaOrderByType
import com.dna.payments.kmm.presentation.state.ManagementResourceUiState
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DnaChartLoading
import com.dna.payments.kmm.presentation.ui.common.DnaChartWidget
import com.dna.payments.kmm.presentation.ui.common.DnaFilter
import com.dna.payments.kmm.presentation.ui.common.DnaTextSwitch
import com.dna.payments.kmm.presentation.ui.features.overview_report.widgets.payment_status_bottom_sheet.PaymentStatusBottomSheet
import com.dna.payments.kmm.presentation.ui.features.overview_report.widgets.payment_status_bottom_sheet.PaymentStatusDropDownContent
import org.koin.core.parameter.parametersOf

class ChartWidget(
    private val overviewReportType: OverviewReportType,
    private val dateSelection: DateSelection,
    private val selectedCurrency: Currency,
    private val widgetType: OverviewReportWidgetType,
) : Screen {
    override val key: ScreenKey
        get() = "ChartWidget${overviewReportType}${widgetType})"

    @Composable
    override fun Content() {

        val openPaymentStatusListBottomSheet = rememberSaveable { mutableStateOf(false) }

        var selectedTabIndex by remember {
            mutableStateOf(DnaOrderByType.AMOUNT.index)
        }

        val viewModel = getScreenModel<ChartViewModel> {
            parametersOf(widgetType, overviewReportType)
        }

        val state by viewModel.uiState.collectAsState()

        LaunchedEffect(
            key1 = dateSelection,
            key2 = selectedCurrency
        ) {
            viewModel.setEvent(
                ChartContract.Event.OnFetchData(
                    dateSelection = dateSelection,
                    currency = selectedCurrency
                )
            )
        }

        Column(
            modifier = Modifier.padding(Paddings.medium)
        ) {
            Row {
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
                                ChartContract.Event.OnPaymentStatusChange(
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
                resourceUiState = state.resourceUiState,
                onCheckAgain = {},
                onTryAgain = {},
                successView = { responsePair ->
                    val dataPair = when (selectedTabIndex) {
                        DnaOrderByType.AMOUNT.index -> Pair(responsePair.first, selectedCurrency)
                        else -> Pair(responsePair.second, null)
                    }
                    DnaChartWidget(
                        dataPair.first,
                        dataPair.second
                    )
                },
                loadingView = {
                    DnaChartLoading()
                },
                msgCheckAgain = {}
            )
        }
    }
}