package com.dna.payments.kmm.presentation.ui.features.overview

import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface OverviewContract {
    sealed interface Event : UiEvent {
        data class OnPageChanged(
            val position: Int
        ) : Event
    }

    data class State(
        val selectedPage: Int = 0
    ) : UiState

    sealed interface Effect : UiEffect {
        data class OnPageChanged(
            val position: Int
        ) : Effect
    }
}