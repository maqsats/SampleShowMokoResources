package com.dna.payments.kmm.presentation.ui.features.login

import androidx.compose.runtime.MutableState
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface LoginContract {
    sealed interface Event : UiEvent {
        data object OnLoginClicked : Event
        data object OnEmailFieldChanged : Event
        data object OnPasswordFieldChanged : Event
    }

    data class State(
        val email: TextFieldUiState,
        val password: TextFieldUiState,
        val isLoginEnabled: MutableState<Boolean>,
        val authorization: ResourceUiState<Unit>
    ) : UiState

    sealed interface Effect : UiEffect {
    }
}


