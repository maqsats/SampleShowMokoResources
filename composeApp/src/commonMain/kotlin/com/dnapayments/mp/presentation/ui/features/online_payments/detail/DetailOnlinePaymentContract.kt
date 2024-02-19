package com.dnapayments.mp.presentation.ui.features.online_payments.detail

import com.dnapayments.mp.domain.model.online_payments.OnlinePaymentOperationType
import com.dnapayments.mp.domain.model.transactions.Transaction
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

interface DetailOnlinePaymentContract {
    sealed interface Event : UiEvent {
        data class OnTransactionChanged(
            val transactionId: String
        ) : Event

        data class OnTransactionGet(
            val transaction: Transaction
        ) : Event
    }

    data class State(
        val detailPaymentState: ResourceUiState<Transaction?>,
        val operationTypeList: List<OnlinePaymentOperationType>,
        val isReceiptVisible: Boolean
    ) : UiState

    sealed interface Effect : UiEffect {
        data object OnTransactionFetchedSuccess : Effect
    }
}


