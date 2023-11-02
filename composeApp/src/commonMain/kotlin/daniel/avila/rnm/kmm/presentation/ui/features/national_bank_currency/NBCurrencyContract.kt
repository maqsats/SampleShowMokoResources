package daniel.avila.rnm.kmm.presentation.ui.features.national_bank_currency

import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency
import daniel.avila.rnm.kmm.domain.model.time_period_tab.TimePeriod
import daniel.avila.rnm.kmm.domain.params.CurrencyCode
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.mvi.UiEffect
import daniel.avila.rnm.kmm.presentation.mvi.UiEvent
import daniel.avila.rnm.kmm.presentation.mvi.UiState

interface NBCurrencyContract {

    sealed interface Event : UiEvent {
        data class OnFetchData(
            val nbCurrencyParams: Pair<TimePeriod, CurrencyCode>,
        ) : Event

        data object OnTryCheckAgainClick : Event
    }

    data class State(
        val nationalBankCurrencyList: ResourceUiState<List<NationalBankCurrency>>
    ) : UiState

    sealed interface Effect : UiEffect
}


