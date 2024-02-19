package com.dnapayments.mp.presentation.ui.features.pos_payments

import com.dnapayments.mp.domain.model.date_picker.DatePickerPeriod
import com.dnapayments.mp.domain.model.date_picker.DateSelection
import com.dnapayments.mp.domain.model.pos_payments.PosPaymentStatusV2
import com.dnapayments.mp.domain.model.transactions.pos.PosTransaction
import com.dnapayments.mp.presentation.model.PagingUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

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


