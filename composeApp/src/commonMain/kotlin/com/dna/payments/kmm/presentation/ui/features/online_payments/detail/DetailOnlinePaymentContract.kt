package com.dna.payments.kmm.presentation.ui.features.online_payments.detail

import com.dna.payments.kmm.domain.model.online_payments.OnlinePaymentOperationType
import com.dna.payments.kmm.domain.model.transactions.Transaction
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

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


