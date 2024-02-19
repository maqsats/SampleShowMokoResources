package com.dnapayments.mp.presentation.ui.features.restore_password

import androidx.compose.runtime.MutableState
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.model.TextFieldUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

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


