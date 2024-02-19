package com.dnapayments.mp.domain.model.overview_report

import androidx.compose.runtime.Immutable

@Immutable
enum class OverviewReportWidgetType {
    APPROVAL_RATE,
    AVERAGE_METRICS,
    TRANSACTIONS,
    CHARGED_TRANSACTIONS_COMPARISON,
    PRODUCT_GUIDE,
    PAYMENT_METHODS_CHART,
    CARD_SCHEMES_CHART,
    ISSUING_BANKS_CHART
}