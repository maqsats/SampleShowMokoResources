package com.dnapayments.mp.presentation.ui.features.overview_report.widgets.approval_average_metrics

import com.dnapayments.mp.domain.model.average_metrics.Metric
import com.dnapayments.mp.domain.model.currency.Currency
import com.dnapayments.mp.domain.model.date_picker.DateSelection
import com.dnapayments.mp.domain.model.overview_report.Summary
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

interface ApprovalAverageMetricsContract {

    sealed interface Event : UiEvent {
        data class OnFetchData(
            val dateSelection: DateSelection,
            val currency: Currency
        ) : Event
    }

    data class State(
        val paymentsSummaryList: ResourceUiState<Pair<List<Summary>, List<Metric>>>
    ) : UiState

    sealed interface Effect : UiEffect
}


