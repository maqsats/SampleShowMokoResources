package com.dna.payments.kmm.presentation.ui.features.overview

import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface OverviewContract {
    sealed interface Event : UiEvent {
        data class OnPageChanged(
            val position: Int
        ) : Event

        data class OnDateSelection(val datePickerPeriod: DatePickerPeriod) : Event
    }

    data class State(
        val selectedPage: Int = 0,
        val dateRange: Pair<DatePickerPeriod, DateSelection>
    ) : UiState

    sealed interface Effect : UiEffect {
        data class OnPageChanged(
            val position: Int
        ) : Effect
    }
}