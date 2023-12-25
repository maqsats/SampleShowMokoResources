package com.dna.payments.kmm.presentation.ui.features.overview

import cafe.adriel.voyager.core.model.screenModelScope
import com.dna.payments.kmm.domain.interactors.use_cases.currency.CurrencyUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.GetDateRangeUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.transaction.TransactionUseCase
import com.dna.payments.kmm.domain.model.date_picker.Menu
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import com.dna.payments.kmm.utils.constants.Constants.GBP
import com.dna.payments.kmm.utils.extension.findIndexOfDefaultCurrency
import com.dna.payments.kmm.utils.extension.getDefaultDateRange
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val getDateRangeUseCase: GetDateRangeUseCase,
    private val transactionUseCase: TransactionUseCase,
    private val currencyUseCase: CurrencyUseCase
) : BaseViewModel<OverviewContract.Event, OverviewContract.State, OverviewContract.Effect>() {

    init {
        setState {
            copy(dateRange = getDateRangeUseCase(Menu.OVERVIEW))
        }
        getCurrencies()
    }

    override fun createInitialState() = OverviewContract.State(
        dateRange = getDefaultDateRange(),
        currencies = ResourceUiState.Idle,
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
            }
            is OverviewContract.Event.OnCurrencyChange -> {
                setState {
                    copy(indexOfSelectedCurrency = event.selectedCurrencyIndex)
                }
            }
        }
    }

    private fun getCurrencies() {
        setState { copy(currencies = ResourceUiState.Loading) }
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
            setState {
                copy(
                    currencies = currencies,
                    indexOfSelectedCurrency = when (currencies) {
                        is ResourceUiState.Success ->
                            currencies.data.findIndexOfDefaultCurrency(GBP)
                        else -> {
                            0
                        }
                    }
                )
            }
        }
    }
}