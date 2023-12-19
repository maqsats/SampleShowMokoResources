package com.dna.payments.kmm.presentation.ui.features.payment_links

import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkByPeriod
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface PaymentLinksContract {
    sealed interface Event : UiEvent {
        data object OnInit : Event
        data class OnPageChanged(
            val position: Int
        ) : Event


        data class OnDateSelection(val datePickerPeriod: DatePickerPeriod) : Event
    }

    data class State(
        val paymentLinkList: ResourceUiState<List<PaymentLinkByPeriod>>,
        val hasPermission: Boolean,
        val selectedPage: Int = 0,
        val dateRange: Pair<DatePickerPeriod, DateSelection>
    ) : UiState

    sealed interface Effect : UiEffect {
        data class OnPageChanged(
            val position: Int
        ) : Effect
    }
}


