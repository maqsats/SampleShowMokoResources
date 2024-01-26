package com.dna.payments.kmm.presentation.ui.features.overview

import cafe.adriel.voyager.core.model.screenModelScope
import com.dna.payments.kmm.domain.interactors.data_factory.overview.OverviewDataFactory
import com.dna.payments.kmm.domain.interactors.data_factory.product_guide.ProductGuideDataFactory
import com.dna.payments.kmm.domain.interactors.use_cases.currency.CurrencyUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DateHelper
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.GetDateRangeUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.reports.OnlineSummaryGraphUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.reports.PosSummaryGraphUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.transaction.TransactionUseCase
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.date_picker.Menu
import com.dna.payments.kmm.domain.model.status_summary.PaymentStatus
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import com.dna.payments.kmm.utils.constants.Constants.GBP
import com.dna.payments.kmm.utils.extension.convertToServerFormat
import com.dna.payments.kmm.utils.extension.findIndexOfDefaultCurrency
import com.dna.payments.kmm.utils.extension.getDefaultDateRange
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val getDateRangeUseCase: GetDateRangeUseCase,
    private val transactionUseCase: TransactionUseCase,
    private val currencyUseCase: CurrencyUseCase,
    private val overviewDataFactory: OverviewDataFactory,
    private val productGuideDataFactory: ProductGuideDataFactory,
    private val posSummaryGraphUseCase: PosSummaryGraphUseCase,
    private val onlineSummaryGraphUseCase: OnlineSummaryGraphUseCase,
    private val dateHelper: DateHelper
) : BaseViewModel<OverviewContract.Event, OverviewContract.State, OverviewContract.Effect>() {

    init {
        setState {
            copy(
                dateRange = getDateRangeUseCase(Menu.OVERVIEW),
                overviewWidgetItems = overviewDataFactory(),
                productGuideList = productGuideDataFactory()
            )
        }
        getCurrencies()
    }

    override fun createInitialState() = OverviewContract.State(
        dateRange = getDefaultDateRange(),
        currencies = ResourceUiState.Loading,
        overviewWidgetItems = emptyList(),
        posPaymentsSummaryList = ResourceUiState.Loading,
        onlinePaymentsSummaryList = ResourceUiState.Loading,
        onlinePaymentsMetricList = ResourceUiState.Loading,
        posPaymentsMetricList = ResourceUiState.Loading,
        productGuideList = emptyList(),
        posPaymentsGraphSummary = ResourceUiState.Loading,
        onlinePaymentsGraphSummary = ResourceUiState.Loading
    )

    override fun handleEvent(event: OverviewContract.Event) {
        when (event) {
            is OverviewContract.Event.OnPageChanged -> {
                setState {
                    copy(selectedPage = event.position)
                }
                setEffect {
                    OverviewContract.Effect.OnPageChanged(event.position)
                }
            }
            is OverviewContract.Event.OnDateSelection -> {
                setState {
                    copy(
                        dateRange = Pair(
                            event.datePickerPeriod,
                            getDateRangeUseCase(event.datePickerPeriod)
                        )
                    )
                }

                fetchData(currentState.selectedCurrency)
            }
            is OverviewContract.Event.OnCurrencyChange -> {
                setState {
                    copy(selectedCurrency = event.selectedCurrency)
                }

                fetchData(event.selectedCurrency)
            }
            OverviewContract.Event.OnMerchantChanged -> {
                with(currentState) {
                    if (posPaymentsGraphSummary !is ResourceUiState.Loading) {
                        getCurrencies()
                    }
                }
            }
        }
    }

    private fun getCurrencies() {
        screenModelScope.launch {
            val currencies = when (val result = currencyUseCase()) {
                is Response.Success -> {
                    ResourceUiState.Success(result.data)
                }
                is Response.Error -> {
                    ResourceUiState.Error(result.error)
                }
                is Response.NetworkError -> {
                    ResourceUiState.NetworkError
                }
                is Response.TokenExpire -> {
                    ResourceUiState.TokenExpire
                }
            }

            val indexOfSelectedCurrency = when (currencies) {
                is ResourceUiState.Success -> {
                    currencies.data.findIndexOfDefaultCurrency(GBP)
                }
                else -> {
                    0
                }
            }

            val defaultCurrency = when (currencies) {
                is ResourceUiState.Success -> {
                    currencies.data[indexOfSelectedCurrency]
                }
                else -> {
                    Currency(GBP)
                }
            }

            fetchData(defaultCurrency)

            setState {
                copy(
                    currencies = currencies,
                    selectedCurrency = defaultCurrency
                )
            }
        }
    }

    private fun fetchData(defaultCurrency: Currency) {
        getPosPaymentsSummary(defaultCurrency)

        getPosPaymentsGraphSummary(defaultCurrency)

        getOnlinePaymentsGraphSummary(defaultCurrency)

        getOnlinePaymentsSummary(defaultCurrency)
    }

    private fun getPosPaymentsGraphSummary(selectedCurrency: Currency) {
        setState {
            copy(
                posPaymentsGraphSummary = ResourceUiState.Loading
            )
        }
        screenModelScope.launch {
            val intervalType = dateHelper.findIntervalType(currentState.dateRange.second)
            val summary = when (val result = posSummaryGraphUseCase.getPosSummaryGraph(
                startDate = currentState.dateRange.second.startDate,
                endDate = currentState.dateRange.second.endDate,
                currency = selectedCurrency.name,
                intervalType = intervalType
            )) {
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
                    posPaymentsGraphSummary = summary
                )
            }
        }
    }

    private fun getOnlinePaymentsGraphSummary(selectedCurrency: Currency) {
        setState {
            copy(
                onlinePaymentsGraphSummary = ResourceUiState.Loading
            )
        }
        screenModelScope.launch {
            val intervalType = dateHelper.findIntervalType(currentState.dateRange.second)
            val summary = when (val result = onlineSummaryGraphUseCase.getOnlineSummaryGraph(
                startDate = currentState.dateRange.second.startDate,
                endDate = currentState.dateRange.second.endDate,
                currency = selectedCurrency.name,
                status = PaymentStatus.CHARGE.name,
                intervalType = intervalType
            )) {
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
                    onlinePaymentsGraphSummary = summary
                )
            }
        }
    }

    private fun getPosPaymentsSummary(selectedCurrency: Currency) {
        setState {
            copy(
                posPaymentsSummaryList = ResourceUiState.Loading,
                posPaymentsMetricList = ResourceUiState.Loading,
            )
        }
        screenModelScope.launch {
            val posPaymentsSummaryList =
                when (val result = transactionUseCase.getPosPaymentsSummary(
                    startDate = currentState.dateRange.second.startDate,
                    endDate = currentState.dateRange.second.endDate,
                    currency = selectedCurrency.name
                )) {
                    is Response.Success -> {
                        setState {
                            copy(
                                posPaymentsMetricList = ResourceUiState.Success(
                                    transactionUseCase.getAverageMetricsPosPayments(
                                        result.data,
                                        currentState.dateRange.second.daysBetween
                                    )
                                )
                            )
                        }
                        ResourceUiState.Success(result.data)
                    }
                    is Response.Error -> {
                        ResourceUiState.Error(result.error)
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
                    posPaymentsSummaryList = posPaymentsSummaryList,
                )
            }
        }
    }

    private fun getOnlinePaymentsSummary(selectedCurrency: Currency) {
        setState {
            copy(
                onlinePaymentsSummaryList = ResourceUiState.Loading,
                onlinePaymentsMetricList = ResourceUiState.Loading
            )
        }
        screenModelScope.launch {
            val onlinePaymentsSummary =
                when (val result = transactionUseCase.getMainSummary(
                    startDate = currentState.dateRange.second.startDate,
                    endDate = currentState.dateRange.second.endDate,
                    currency = selectedCurrency.name
                )) {
                    is Response.Success -> {
                        setState {
                            copy(
                                onlinePaymentsMetricList = ResourceUiState.Success(
                                    transactionUseCase.getAverageMetricsOnlinePayments(
                                        result.data,
                                        currentState.dateRange.second.daysBetween
                                    )
                                )
                            )
                        }
                        ResourceUiState.Success(result.data)
                    }
                    is Response.Error -> {
                        ResourceUiState.Error(result.error)
                    }
                    is Response.NetworkError -> {
                        ResourceUiState.NetworkError
                    }
                    is Response.TokenExpire -> {
                        ResourceUiState.TokenExpire
                    }
                }
            setState {
                copy(onlinePaymentsSummaryList = onlinePaymentsSummary)
            }
        }
    }
}