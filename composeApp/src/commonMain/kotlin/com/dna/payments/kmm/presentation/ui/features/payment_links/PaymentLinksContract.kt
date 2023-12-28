package com.dna.payments.kmm.presentation.ui.features.payment_links

import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkByPeriod
import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkStatus
import com.dna.payments.kmm.presentation.model.PagingUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface PaymentLinksContract {
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
        val paymentLinkList: List<PaymentLinkByPeriod>,
        val pagingUiState: PagingUiState,
        val hasPermission: Boolean,
        val selectedPage: Int = 0,
        val dateRange: Pair<DatePickerPeriod, DateSelection>,
        val statusList: List<PaymentLinkStatus>,
        val indexOfSelectedStatus: Int = 0
    ) : UiState

    sealed interface Effect : UiEffect {
        data class OnPageChanged(
            val position: Int
        ) : Effect
    }
}


