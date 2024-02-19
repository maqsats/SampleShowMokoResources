package com.dnapayments.mp.presentation.ui.features.online_payments

import com.dnapayments.mp.domain.model.date_picker.DatePickerPeriod
import com.dnapayments.mp.domain.model.date_picker.DateSelection
import com.dnapayments.mp.domain.model.online_payments.OnlinePaymentStatus
import com.dnapayments.mp.domain.model.transactions.Transaction
import com.dnapayments.mp.presentation.model.PagingUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

interface OnlinePaymentsContract {
    sealed interface Event : UiEvent {
        data class OnPageChanged(
            val position: Int
        ) : Event

        data object OnLoadMore : Event
        data object OnRefresh : Event
        data class OnDateSelection(val datePickerPeriod: DatePickerPeriod) : Event
        data class OnStatusChange(val selectedStatusIndex: Int) : Event
    }

    data class State(
        val onlinePaymentList: List<Transaction>,
        val hasPermission: Boolean,
        val pagingUiState: PagingUiState,
        val selectedPage: Int = 0,
        val dateRange: Pair<DatePickerPeriod, DateSelection>,
        val statusList: List<OnlinePaymentStatus>,
        val indexOfSelectedStatus: Int = 0
    ) : UiState

    sealed interface Effect : UiEffect {
        data class OnPageChanged(
            val position: Int
        ) : Effect
    }
}


