package com.dna.payments.kmm.presentation.ui.features.overview

import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface OverviewContract {
    sealed interface Event : UiEvent {
        data class OnPageChanged(
            val position: Int
        ) : Event

        data class OnDateSelection(val datePickerPeriod: DatePickerPeriod) : Event
        data class OnCurrencyChange(val selectedCurrencyIndex: Int) : Event
    }

    data class State(
        val selectedPage: Int = 0,
        val dateRange: Pair<DatePickerPeriod, DateSelection>,
        val currencies: ResourceUiState<List<Currency>>,
        val indexOfSelectedCurrency: Int = 0
    ) : UiState

    sealed interface Effect : UiEffect {
        data class OnPageChanged(
            val position: Int
        ) : Effect
    }
}