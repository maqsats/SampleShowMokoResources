package com.dna.payments.kmm.presentation.ui.features.payment_methods_add_domain.first_step

import androidx.compose.runtime.MutableState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface AddDomainFirstStepContract {
    sealed interface Event : UiEvent {
        data object OnDomainFieldChanged : Event
    }

    data class State(
        val domain: TextFieldUiState,
        val isNextEnabled: MutableState<Boolean>,
    ) : UiState

    sealed interface Effect : UiEffect
}


