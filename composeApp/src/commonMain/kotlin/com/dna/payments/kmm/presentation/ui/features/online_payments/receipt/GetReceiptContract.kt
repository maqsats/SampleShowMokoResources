package com.dna.payments.kmm.presentation.ui.features.online_payments.receipt

import androidx.compose.runtime.MutableState
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface GetReceiptContract {
    sealed interface Event : UiEvent {
        data class OnSendClicked(
            val transactionId: String
        ) : Event

        data object OnDownloadClicked : Event

        data object OnEmailFieldChanged : Event
    }

    data class State(
        val sendReceiptState: ResourceUiState<Unit>,
        val email: TextFieldUiState,
        val isButtonEnabled: MutableState<Boolean>
    ) : UiState

    sealed interface Effect : UiEffect {
        data object OnSendSuccess : Effect
    }
}


