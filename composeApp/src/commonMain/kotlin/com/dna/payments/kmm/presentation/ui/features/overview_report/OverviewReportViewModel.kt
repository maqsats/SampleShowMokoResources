package com.dna.payments.kmm.presentation.ui.features.overview_report

import cafe.adriel.voyager.core.model.screenModelScope
import com.dna.payments.kmm.domain.interactors.data_factory.overview.OverviewReportDataFactory
import com.dna.payments.kmm.domain.interactors.use_cases.currency.CurrencyUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.GetDateRangeUseCase
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.date_picker.Menu
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import com.dna.payments.kmm.utils.constants.Constants.GBP
import com.dna.payments.kmm.utils.extension.findIndexOfDefaultCurrency
import com.dna.payments.kmm.utils.extension.getDefaultDateRange
import kotlinx.coroutines.launch

class OverviewReportViewModel(
    private val menu: Menu,
    private val getDateRangeUseCase: GetDateRangeUseCase,
    private val currencyUseCase: CurrencyUseCase,
    private val overviewReportDataFactory: OverviewReportDataFactory
) : BaseViewModel<OverviewReportContract.Event, OverviewReportContract.State, OverviewReportContract.Effect>() {

    init {
        setState {
            copy(
                dateRange = getDateRangeUseCase(menu),
                overviewReportWidgetItems = overviewReportDataFactory(menu),
                menuType = menu
            )
        }
        getCurrencies()
    }

    override fun createInitialState() = OverviewReportContract.State(
        dateRange = getDefaultDateRange(),
        currencies = ResourceUiState.Loading,
        overviewReportWidgetItems = emptyList(),
        selectedCurrency = Currency(GBP),
        menuType = Menu.OVERVIEW
    )

    override fun handleEvent(event: OverviewReportContract.Event) {
        when (event) {
            is OverviewReportContract.Event.OnPageChanged -> {
                setState {
                    copy(selectedPage = event.position)
                }
                setEffect {
                    OverviewReportContract.Effect.OnPageChanged(event.position)
                }
            }
            is OverviewReportContract.Event.OnDateSelection -> {
                setState {
                    copy(
                        dateRange = Pair(
                            event.datePickerPeriod,
                            getDateRangeUseCase(event.datePickerPeriod)
                        )
                    )
                }
            }
            is OverviewReportContract.Event.OnCurrencyChange -> {
                setState {
                    copy(selectedCurrency = event.selectedCurrency)
                }
            }
            is OverviewReportContract.Event.OnMerchantChanged -> {
                with(currentState) {
                    if (currencies !is ResourceUiState.Loading) {
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

            setState {
                copy(
                    currencies = currencies,
                    selectedCurrency = defaultCurrency
                )
            }
        }
    }
}