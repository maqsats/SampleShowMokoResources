package com.dna.payments.kmm.presentation.ui.features.overview

import com.dna.payments.kmm.domain.model.average_metrics.Metric
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.domain.model.overview.OverviewType
import com.dna.payments.kmm.domain.model.overview.OverviewWidgetItem
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentSummary
import com.dna.payments.kmm.domain.model.product_guide.ProductGuide
import com.dna.payments.kmm.domain.model.reports.HistogramEntry
import com.dna.payments.kmm.domain.model.status_summary.Summary
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState
import com.dna.payments.kmm.utils.constants.Constants.GBP

interface OverviewContract {
    sealed interface Event : UiEvent {
        data class OnPageChanged(
            val position: Int
        ) : Event

        data class OnDateSelection(val datePickerPeriod: DatePickerPeriod) : Event
        data class OnCurrencyChange(val selectedCurrency: Currency) : Event
        data object OnMerchantChanged : Event
    }

    data class State(
        val selectedPage: Int = OverviewType.POS_PAYMENTS.pageId,
        val dateRange: Pair<DatePickerPeriod, DateSelection>,
        val currencies: ResourceUiState<List<Currency>>,
        val selectedCurrency: Currency = Currency(GBP),
        val overviewWidgetItems: List<OverviewWidgetItem>,
        val posPaymentsSummaryList: ResourceUiState<List<PosPaymentSummary>>,
        val onlinePaymentsSummaryList: ResourceUiState<List<Summary>>,
        val onlinePaymentsMetricList: ResourceUiState<List<Metric>>,
        val posPaymentsMetricList: ResourceUiState<List<Metric>>,
        val productGuideList: List<ProductGuide>,
        val posPaymentsGraphSummary: ResourceUiState<Pair<HistogramEntry, HistogramEntry>>,
        val onlinePaymentsGraphSummary: ResourceUiState<Pair<HistogramEntry, HistogramEntry>>
    ) : UiState

    sealed interface Effect : UiEffect {
        data class OnPageChanged(
            val position: Int
        ) : Effect
    }
}