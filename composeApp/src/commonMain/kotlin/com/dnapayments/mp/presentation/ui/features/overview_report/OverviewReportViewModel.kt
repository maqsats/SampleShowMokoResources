package com.dnapayments.mp.presentation.ui.features.overview_report

import cafe.adriel.voyager.core.model.screenModelScope
import com.dnapayments.mp.domain.interactors.data_factory.overview_report.OverviewReportDataFactory
import com.dnapayments.mp.domain.interactors.use_cases.currency.CurrencyUseCase
import com.dnapayments.mp.domain.interactors.use_cases.date_picker.GetDateRangeUseCase
import com.dnapayments.mp.domain.model.currency.Currency
import com.dnapayments.mp.domain.model.date_picker.Menu
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.mvi.BaseViewModel
import com.dnapayments.mp.utils.constants.Constants.GBP
import com.dnapayments.mp.utils.extension.findIndexOfDefaultCurrency
import com.dnapayments.mp.utils.extension.getDefaultDateRange
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
        currencies = ResourceUiState.Idle,
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
            OverviewReportContract.Event.OnRefresh -> {
                getCurrencies()
            }
        }
    }

    private fun getCurrencies() {
        screenModelScope.launch {
            setState {
                copy(
                    currencies = ResourceUiState.Loading
                )
            }

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