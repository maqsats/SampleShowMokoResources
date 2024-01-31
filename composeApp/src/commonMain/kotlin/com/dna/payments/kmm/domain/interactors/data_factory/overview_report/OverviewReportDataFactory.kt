package com.dna.payments.kmm.domain.interactors.data_factory.overview_report

import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.date_picker.Menu
import com.dna.payments.kmm.domain.model.overview_report.OverviewReportType
import com.dna.payments.kmm.domain.model.overview_report.OverviewReportWidgetItem
import com.dna.payments.kmm.domain.model.overview_report.OverviewReportWidgetType

class OverviewReportDataFactory {

    operator fun invoke(menu: Menu): List<OverviewReportWidgetItem> {
        val approvalRates = listOf(
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.APPROVAL_RATE,
                overviewReportType = OverviewReportType.ONLINE_PAYMENTS,
                title = MR.strings.approval_rate
            ),
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.APPROVAL_RATE,
                overviewReportType = OverviewReportType.POS_PAYMENTS,
                title = MR.strings.approval_rate
            )
        )

        val averageMetrics = listOf(
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.AVERAGE_METRICS,
                overviewReportType = OverviewReportType.ONLINE_PAYMENTS,
                title = MR.strings.average_metrics
            ),
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.AVERAGE_METRICS,
                overviewReportType = OverviewReportType.POS_PAYMENTS,
                title = MR.strings.average_metrics
            )
        )

        val transactions = listOf(
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.TRANSACTIONS,
                overviewReportType = OverviewReportType.ONLINE_PAYMENTS,
                title = if (menu == Menu.OVERVIEW) MR.strings.charged_transactions else MR.strings.transactions
            ),
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.TRANSACTIONS,
                overviewReportType = OverviewReportType.POS_PAYMENTS,
                title = if (menu == Menu.OVERVIEW) MR.strings.all_pos_transactions else MR.strings.pos_transactions
            ),
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.CHARGED_TRANSACTIONS_COMPARISON,
                overviewReportType = OverviewReportType.ONLINE_PAYMENTS,
                title = if (menu == Menu.OVERVIEW) MR.strings.charged_transactions_comparison else MR.strings.transactions_comparison
            ),
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.CHARGED_TRANSACTIONS_COMPARISON,
                overviewReportType = OverviewReportType.POS_PAYMENTS,
                title = if (menu == Menu.OVERVIEW) MR.strings.all_transactions_comparison else MR.strings.transactions_comparison
            )
        )

        val productGuides = listOf(
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.PRODUCT_GUIDE,
                overviewReportType = OverviewReportType.ONLINE_PAYMENTS,
                title = MR.strings.all
            ),
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.PRODUCT_GUIDE,
                overviewReportType = OverviewReportType.POS_PAYMENTS,
                title = MR.strings.all
            )
        )

        val charts = listOf(
            // POS payments
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.CARD_SCHEMES_CHART,
                overviewReportType = OverviewReportType.POS_PAYMENTS,
                title = MR.strings.card_scheme
            ),
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.ISSUING_BANKS_CHART,
                overviewReportType = OverviewReportType.POS_PAYMENTS,
                title = MR.strings.issuing_banks
            ),
            // Online payments
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.PAYMENT_METHODS_CHART,
                overviewReportType = OverviewReportType.ONLINE_PAYMENTS,
                title = MR.strings.payment_methods
            ),
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.CARD_SCHEMES_CHART,
                overviewReportType = OverviewReportType.ONLINE_PAYMENTS,
                title = MR.strings.card_methods
            ),
            OverviewReportWidgetItem(
                overviewReportWidgetType = OverviewReportWidgetType.ISSUING_BANKS_CHART,
                overviewReportType = OverviewReportType.ONLINE_PAYMENTS,
                title = MR.strings.issuing_banks
            )
        )

        return when (menu) {
            Menu.OVERVIEW -> approvalRates + averageMetrics + transactions + productGuides
            Menu.REPORTS -> approvalRates + averageMetrics + transactions + charts
            else -> emptyList()
        }
    }
}