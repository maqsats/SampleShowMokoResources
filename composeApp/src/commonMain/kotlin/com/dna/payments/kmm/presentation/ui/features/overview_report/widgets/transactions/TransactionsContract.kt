package com.dna.payments.kmm.presentation.ui.features.overview_report.widgets.transactions

import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.domain.model.payment_status.PaymentStatus
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState
import com.dna.payments.kmm.utils.chart.histogram.HistogramEntry

interface TransactionsContract {

    sealed interface Event : UiEvent {
        data class OnFetchData(
            val dateSelection: DateSelection,
            val currency: Currency
        ) : Event

        data class OnPaymentStatusChange(val paymentStatus: PaymentStatus) : Event
    }

    data class State(
        val paymentsGraphSummary: ResourceUiState<Pair<HistogramEntry, HistogramEntry>>,
        val paymentStatusList: List<PaymentStatus>,
        val selectedPaymentStatus: PaymentStatus,
    ) : UiState

    sealed interface Effect : UiEffect
}


