package com.dna.payments.kmm.presentation.ui.features.forgot_password

import androidx.compose.runtime.MutableState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface ForgotPasswordContract {
    sealed interface Event : UiEvent {
        data object OnButtonClicked : Event
        data object OnEmailFieldChanged : Event
    }

    data class State(
        val email: TextFieldUiState,
        val isButtonEnabled: MutableState<Boolean>,
    ) : UiState

    sealed interface Effect : UiEffect {
    }
}


