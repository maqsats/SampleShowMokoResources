package com.dna.payments.kmm.presentation.ui.features.overview_report.widgets.chart

import cafe.adriel.voyager.core.model.screenModelScope
import com.dna.payments.kmm.domain.interactors.use_cases.reports.card_scheme.GetCardSchemeUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.reports.issuing_banks.GetIssuingBanksUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.reports.payment_methods.GetPaymentMethodsUseCase
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.domain.model.online_payments.OnlinePaymentStatus
import com.dna.payments.kmm.domain.model.overview_report.OverviewReportType
import com.dna.payments.kmm.domain.model.overview_report.OverviewReportWidgetType
import com.dna.payments.kmm.domain.model.overview_report.OverviewReportWidgetType.CARD_SCHEMES_CHART
import com.dna.payments.kmm.domain.model.overview_report.OverviewReportWidgetType.ISSUING_BANKS_CHART
import com.dna.payments.kmm.domain.model.payment_status.PaymentStatus
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentStatus
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.launch

class ChartViewModel(
    private val getIssuingBanksUseCase: GetIssuingBanksUseCase,
    private val getPaymentMethodsUseCase: GetPaymentMethodsUseCase,
    private val getCardSchemeUseCase: GetCardSchemeUseCase,
    private val widgetType: OverviewReportWidgetType,
    overviewReportType: OverviewReportType
) : BaseViewModel<ChartContract.Event, ChartContract.State, ChartContract.Effect>() {

    private lateinit var selectedCurrency: Currency
    private lateinit var dateSelection: DateSelection

    init {
        val paymentPair = when (overviewReportType) {
            OverviewReportType.POS_PAYMENTS -> Pair(PosPaymentStatus.entries, PosPaymentStatus.ALL)
            OverviewReportType.ONLINE_PAYMENTS -> Pair(
                OnlinePaymentStatus.getMainStatuses(),
                OnlinePaymentStatus.CHARGE
            )
        }
        setState {
            copy(
                paymentStatusList = paymentPair.first,
                selectedPaymentStatus = paymentPair.second
            )
        }
    }

    override fun createInitialState() = ChartContract.State(
        resourceUiState = ResourceUiState.Idle,
        paymentStatusList = emptyList(),
        selectedPaymentStatus = OnlinePaymentStatus.CHARGE,
    )

    override fun handleEvent(event: ChartContract.Event) {
        when (event) {
            is ChartContract.Event.OnFetchData -> {
                if (::selectedCurrency.isInitialized && ::dateSelection.isInitialized
                    && selectedCurrency == event.currency && dateSelection == event.dateSelection
                )
                    return

                selectedCurrency = event.currency
                dateSelection = event.dateSelection

                getIssuingBanks()
            }
            is ChartContract.Event.OnPaymentStatusChange -> {
                if (!::selectedCurrency.isInitialized || !::dateSelection.isInitialized) return

                setState {
                    copy(selectedPaymentStatus = event.paymentStatus)
                }

                getIssuingBanks()
            }
        }
    }

    private suspend fun getRequestByPaymentStatus(paymentStatus: PaymentStatus) =
        when (paymentStatus) {
            is OnlinePaymentStatus -> {
                when (widgetType) {
                    ISSUING_BANKS_CHART -> getIssuingBanksUseCase.getOnlinePaymentIssuingBanks(
                        from = dateSelection.startDate,
                        to = dateSelection.endDate,
                        currency = selectedCurrency,
                        paymentStatus = paymentStatus
                    )
                    CARD_SCHEMES_CHART -> getCardSchemeUseCase.getOnlinePaymentsCardScheme(
                        from = dateSelection.startDate,
                        to = dateSelection.endDate,
                        currency = selectedCurrency,
                        paymentStatus = paymentStatus
                    )
                    else -> getPaymentMethodsUseCase.getPaymentMethods(
                        from = dateSelection.startDate,
                        to = dateSelection.endDate,
                        currency = selectedCurrency,
                        paymentStatus = paymentStatus
                    )
                }
            }
            is PosPaymentStatus -> {
                when (widgetType) {
                    ISSUING_BANKS_CHART -> getIssuingBanksUseCase(
                        from = dateSelection.startDate,
                        to = dateSelection.endDate,
                        currency = selectedCurrency,
                        paymentStatus = paymentStatus
                    )
                    else -> getCardSchemeUseCase.getPosPaymentsCardScheme(
                        from = dateSelection.startDate,
                        to = dateSelection.endDate,
                        currency = selectedCurrency,
                        paymentStatus = paymentStatus
                    )
                }
            }
            else -> throw IllegalArgumentException("Unknown payment status")
        }


    private fun getIssuingBanks() {
        setState {
            copy(
                resourceUiState = ResourceUiState.Loading
            )
        }
        screenModelScope.launch {
            val summary =
                when (val result = getRequestByPaymentStatus(currentState.selectedPaymentStatus)) {
                    is Response.Success -> {
                        ResourceUiState.Success(
                            result.data
                        )
                    }
                    is Response.Error -> {
                        ResourceUiState.Error(
                            result.error
                        )
                    }
                    is Response.NetworkError -> {
                        ResourceUiState.NetworkError
                    }
                    is Response.TokenExpire -> {
                        ResourceUiState.TokenExpire
                    }
                }
            setState {
                copy(
                    resourceUiState = summary
                )
            }
        }
    }
}