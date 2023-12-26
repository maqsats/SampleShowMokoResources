package com.dna.payments.kmm.presentation.ui.features.online_payments

import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.domain.model.status_summary.PaymentStatus
import com.dna.payments.kmm.domain.model.transactions.Transaction
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface OnlinePaymentsContract {
    sealed interface Event : UiEvent {
        data object OnInit : Event
        data class OnPageChanged(
            val position: Int
        ) : Event


        data class OnDateSelection(val datePickerPeriod: DatePickerPeriod) : Event
        data class OnStatusChange(val selectedStatusIndex: Int) : Event
    }

    data class State(
        val onlinePaymentList: ResourceUiState<List<Transaction>>,
        val hasPermission: Boolean,
        val selectedPage: Int = 0,
        val dateRange: Pair<DatePickerPeriod, DateSelection>,
        val statusList: List<PaymentStatus>,
        val indexOfSelectedStatus: Int = 0
    ) : UiState

    sealed interface Effect : UiEffect {
        data class OnPageChanged(
            val position: Int
        ) : Effect
    }
}


