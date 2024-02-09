package com.dna.payments.kmm.presentation.ui.features.online_payments.refund

import androidx.compose.runtime.MutableState
import com.dna.payments.kmm.data.model.online_payments.RefundResult
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface OnlinePaymentRefundContract {
    sealed interface Event : UiEvent {
        data class OnRefundClicked(
            val transactionId: String
        ) : Event

        data class OnInit(val amount: Double, val balance: Double) : Event

        data object OnAmountFieldChanged : Event
    }

    data class State(
        val sendReceiptState: ResourceUiState<RefundResult>,
        val amount: TextFieldUiState,
        val balance: MutableState<Double>,
        val isButtonEnabled: MutableState<Boolean>
    ) : UiState

    sealed interface Effect : UiEffect {
        data class OnSuccessfullyRefunded(val id: String) : Effect
    }
}


