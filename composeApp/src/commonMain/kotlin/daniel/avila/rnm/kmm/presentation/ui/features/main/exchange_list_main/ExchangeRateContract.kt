package daniel.avila.rnm.kmm.presentation.ui.features.main.exchange_list_main

import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.domain.params.ExchangeRateParameters
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.mvi.UiEffect
import daniel.avila.rnm.kmm.presentation.mvi.UiEvent
import daniel.avila.rnm.kmm.presentation.mvi.UiState

interface ExchangeRateContract {
    sealed interface Event : UiEvent {
        data object OnTryCheckAgainClick : Event
        data class OnExchangeClick(val idExchangeRate: Int) : Event
        data class OnFetchData(
            val param: ExchangeRateParameters,
            val inputText: String,
            val currencies: Pair<Currency, Currency>
        ) : Event

        data class OnInputValueChange(val inputText: String) : Event
    }

    data class State(
        val exchangeRateState: ResourceUiState<ExchangeRateState>
    ) : UiState

    sealed interface Effect : UiEffect {
        data class NavigateToDetailExchangeRate(val idExchangeRate: Int) : Effect
    }
}

data class ExchangeRateState(
    val exchangeRateList: List<ExchangeRate>,
    val inputText: String,
    val currencies: Pair<Currency, Currency>
)


