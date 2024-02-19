package com.dnapayments.mp.presentation.ui.features.verification_code

import androidx.compose.runtime.MutableState
import com.dnapayments.mp.data.model.request.EmailVerification
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.model.TextFieldUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

interface VerificationCodeContract {
    sealed interface Event : UiEvent {
        data class OnButtonSendClicked(val email: String) : Event
        data class OnButtonResendCodeClicked(val email: String) : Event
        data object OnEmailFieldChanged : Event
    }

    data class State(
        val verificationCode: TextFieldUiState,
        val isButtonEnabled: MutableState<Boolean>,
        val sendCode: ResourceUiState<EmailVerification>
    ) : UiState

    sealed interface Effect : UiEffect {
        data class OnVerificationSuccess(val id: String) : Effect
    }
}


