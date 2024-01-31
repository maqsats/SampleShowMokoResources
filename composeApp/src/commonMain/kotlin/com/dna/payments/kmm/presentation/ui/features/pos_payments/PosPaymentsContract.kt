package com.dna.payments.kmm.presentation.ui.features.pos_payments

import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentStatusV2
import com.dna.payments.kmm.domain.model.transactions.pos.PosTransaction
import com.dna.payments.kmm.presentation.model.PagingUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface PosPaymentsContract {
    sealed interface Event : UiEvent {
        data class OnPageChanged(
            val position: Int
        ) : Event

        data object OnLoadMore : Event
        data object OnRefresh : Event
        data class OnDateSelection(val datePickerPeriod: DatePickerPeriod) : Event
        data class OnStatusChange(val selectedStatus: PosPaymentStatusV2) : Event
    }

    data class State(
        val posPaymentList: MutableList<PosTransaction>,
        val hasPermission: Boolean,
        val pagingUiState: PagingUiState,
        val selectedPage: Int = 0,
        val dateRange: Pair<DatePickerPeriod, DateSelection>,
        val statusList: List<PosPaymentStatusV2>,
        val selectedStatus: PosPaymentStatusV2,
    ) : UiState

    sealed interface Effect : UiEffect {
        data class OnPageChanged(
            val position: Int
        ) : Effect
    }
}


