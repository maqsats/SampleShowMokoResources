package com.dna.payments.kmm.presentation.ui.features.verification_code

import androidx.compose.runtime.MutableState
import com.dna.payments.kmm.data.model.request.EmailVerification
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

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


