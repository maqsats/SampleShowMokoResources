package com.dna.payments.kmm.presentation.ui.features.restore_password

import androidx.compose.runtime.MutableState
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface RestorePasswordContract {
    sealed interface Event : UiEvent {
        data object OnButtonClicked : Event
        data object OnEmailFieldChanged : Event
    }

    data class State(
        val email: TextFieldUiState,
        val isButtonEnabled: MutableState<Boolean>,
        val sendInstruction: ResourceUiState<Unit>
    ) : UiState

    sealed interface Effect : UiEffect {
        data object OnInstructionSuccess : Effect
    }
}


