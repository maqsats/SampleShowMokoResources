package com.dnapayments.mp.presentation.ui.features.new_password

import androidx.compose.runtime.MutableState
import com.dnapayments.mp.domain.model.new_password.PasswordRequirement
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.model.TextFieldUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

interface NewPasswordContract {
    sealed interface Event : UiEvent {
        data class OnButtonClicked(
            val id: String,
            val email: String
        ) : Event

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


