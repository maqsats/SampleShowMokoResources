package com.dnapayments.mp.presentation.ui.features.online_payments.refund

import androidx.compose.runtime.MutableState
import com.dnapayments.mp.data.model.online_payments.RefundResult
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.model.TextFieldUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

interface OnlinePaymentRefundContract {
    sealed interface Event : UiEvent {
        data class OnRefundClicked(
            val transactionId: String
        ) : Event

        data class OnInit(val amount: Double, val balance: Double) : Event

        data object OnAmountFieldChanged : Event
    }

    data class State(
        val refundState: ResourceUiState<RefundResult>,
        val amount: TextFieldUiState,
        val balance: Double,
        val isButtonEnabled: MutableState<Boolean>
    ) : UiState

    sealed interface Effect : UiEffect {
        data class OnSuccessfullyRefunded(val id: String) : Effect
    }
}


