package com.dnapayments.mp.presentation.ui.features.overview_report

import com.dnapayments.mp.domain.model.currency.Currency
import com.dnapayments.mp.domain.model.date_picker.DatePickerPeriod
import com.dnapayments.mp.domain.model.date_picker.DateSelection
import com.dnapayments.mp.domain.model.date_picker.Menu
import com.dnapayments.mp.domain.model.overview_report.OverviewReportType
import com.dnapayments.mp.domain.model.overview_report.OverviewReportWidgetItem
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

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