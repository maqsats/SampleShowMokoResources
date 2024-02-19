package com.dnapayments.mp.presentation.ui.features.payment_methods_add_domain.first_step

import androidx.compose.runtime.MutableState
import com.dnapayments.mp.presentation.model.TextFieldUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

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


