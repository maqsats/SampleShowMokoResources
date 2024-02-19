package com.dnapayments.mp.presentation.ui.features.login

import androidx.compose.runtime.MutableState
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.model.TextFieldUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

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
        data object OnLoginSuccess : Effect
    }
}


