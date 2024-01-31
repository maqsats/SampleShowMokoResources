package com.dna.payments.kmm.presentation.ui.features.overview_report.widgets.approval_average_metrics

import com.dna.payments.kmm.domain.model.average_metrics.Metric
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.domain.model.overview_report.Summary
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

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


