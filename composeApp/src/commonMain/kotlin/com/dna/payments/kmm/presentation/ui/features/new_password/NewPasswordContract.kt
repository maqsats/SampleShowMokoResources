package com.dna.payments.kmm.presentation.ui.features.new_password

import androidx.compose.runtime.MutableState
import com.dna.payments.kmm.domain.model.new_password.PasswordRequirement
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface NewPasswordContract {
    sealed interface Event : UiEvent {
        data object OnButtonClicked : Event
        data object OnPasswordFieldChanged : Event
        data object OnConfirmPasswordFieldChanged : Event
    }

    data class State(
        val password: TextFieldUiState,
        val confirmPassword: TextFieldUiState,
        val isButtonEnabled: MutableState<Boolean>,
        val newPassword: ResourceUiState<Unit>,
        val validationList: MutableState<List<PasswordRequirement>>
    ) : UiState

    sealed interface Effect : UiEffect {
        data object OnResetPasswordSuccess : Effect
    }
}


