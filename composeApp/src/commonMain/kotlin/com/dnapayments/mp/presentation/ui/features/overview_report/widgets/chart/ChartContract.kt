package com.dnapayments.mp.presentation.ui.features.overview_report.widgets.chart

import com.dnapayments.mp.domain.model.currency.Currency
import com.dnapayments.mp.domain.model.date_picker.DateSelection
import com.dnapayments.mp.domain.model.payment_status.PaymentStatus
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState
import com.dnapayments.mp.utils.chart.pie.PieChartData

interface ChartContract {

    sealed interface Event : UiEvent {
        data class OnFetchData(
            val dateSelection: DateSelection,
            val currency: Currency
        ) : Event

        data class OnPaymentStatusChange(val paymentStatus: PaymentStatus) : Event
    }

    data class State(
        val resourceUiState: ResourceUiState<Pair<List<PieChartData>, List<PieChartData>>>,
        val paymentStatusList: List<PaymentStatus>,
        val selectedPaymentStatus: PaymentStatus,
    ) : UiState

    sealed interface Effect : UiEffect
}


