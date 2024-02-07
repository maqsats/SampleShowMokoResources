package com.dna.payments.kmm.presentation.ui.features.overview_report

import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.domain.model.date_picker.Menu
import com.dna.payments.kmm.domain.model.overview_report.OverviewReportType
import com.dna.payments.kmm.domain.model.overview_report.OverviewReportWidgetItem
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface OverviewReportContract {
    sealed interface Event : UiEvent {
        data class OnPageChanged(
            val position: Int
        ) : Event

        data class OnDateSelection(val datePickerPeriod: DatePickerPeriod) : Event
        data class OnCurrencyChange(val selectedCurrency: Currency) : Event
        data object OnMerchantChanged : Event
        data object OnRefresh : Event
    }

    data class State(
        val selectedPage: Int = OverviewReportType.POS_PAYMENTS.pageId,
        val dateRange: Pair<DatePickerPeriod, DateSelection>,
        val currencies: ResourceUiState<List<Currency>>,
        val selectedCurrency: Currency,
        val overviewReportWidgetItems: List<OverviewReportWidgetItem>,
        val menuType: Menu
    ) : UiState

    sealed interface Effect : UiEffect {
        data class OnPageChanged(
            val position: Int
        ) : Effect
    }
}