package daniel.avila.rnm.kmm.presentation.ui.features.calculator.currency

import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.mvi.UiEffect
import daniel.avila.rnm.kmm.presentation.mvi.UiEvent
import daniel.avila.rnm.kmm.presentation.mvi.UiState

interface CurrencyContract {

    sealed interface Event : UiEvent {
        data object OnCurrencyClick : Event
    }

    data class State(
        val currencies: ResourceUiState<List<Currency>>
    ) : UiState

    sealed interface Effect : UiEffect {
        data class NavigateToCurrencyDialog(val currencies: List<Currency>) : Effect
    }
}


