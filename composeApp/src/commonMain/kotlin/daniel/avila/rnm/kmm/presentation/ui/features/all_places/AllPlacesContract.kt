package daniel.avila.rnm.kmm.presentation.ui.features.all_places

import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.mvi.UiEffect
import daniel.avila.rnm.kmm.presentation.mvi.UiEvent
import daniel.avila.rnm.kmm.presentation.mvi.UiState

interface AllPlacesContract {

    sealed interface Event : UiEvent {
        data object OnTryCheckAgainClick : Event
    }

    data class State(
        val nationalBankCurrencyList: ResourceUiState<List<NationalBankCurrency>>,
        val exchangerList: ResourceUiState<List<ExchangeRate>>,
    ) : UiState

    sealed interface Effect : UiEffect
}


