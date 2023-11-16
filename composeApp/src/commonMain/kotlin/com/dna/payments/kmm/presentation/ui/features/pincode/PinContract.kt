package com.dna.payments.kmm.presentation.ui.features.pincode

import androidx.compose.runtime.MutableState
import com.dna.payments.kmm.domain.model.pincode.Code
import com.dna.payments.kmm.domain.model.pincode.PinState
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface PinContract {
    sealed interface Event : UiEvent {
        data class OnDigit(val digit: String) : Event
        data object OnErase : Event
        data object OnLogout : Event
        data object OnBiometricClick : Event
    }

    data class State(
        val codePin: MutableState<Code.Pin>,
        val getAccessToken: ResourceUiState<Unit>,
        val pinState: MutableState<PinState>,
        val showBiometric: MutableState<Boolean>,
    ) : UiState

    sealed interface Effect : UiEffect {
        data object OnPinCorrect : Effect
        data object OnLogout : Effect
    }
}


