package com.dnapayments.mp.presentation.ui.features.overview_report.widgets.transactions

import com.dnapayments.mp.domain.model.currency.Currency
import com.dnapayments.mp.domain.model.date_picker.DateSelection
import com.dnapayments.mp.domain.model.payment_status.PaymentStatus
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState
import com.dnapayments.mp.utils.chart.histogram.HistogramEntry

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


