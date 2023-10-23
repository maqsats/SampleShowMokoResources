package daniel.avila.rnm.kmm.presentation.ui.features.main.exchange_list_main

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
        data class OnFetchData(val param: ExchangeRateParameters) : Event
    }

    data class State(
        val exchangeRateList: ResourceUiState<List<ExchangeRate>>
    ) : UiState

    sealed interface Effect : UiEffect {
        data class NavigateToDetailExchangeRate(val idExchangeRate: Int) : Effect
    }
}


