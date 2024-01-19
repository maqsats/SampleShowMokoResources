package com.dna.payments.kmm.domain.interactors.data_factory.overview

import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.overview.OverviewType
import com.dna.payments.kmm.domain.model.overview.OverviewWidgetItem
import com.dna.payments.kmm.domain.model.overview.OverviewWidgetType

class OverviewDataFactory {
    operator fun invoke(): List<OverviewWidgetItem> {
        return listOf(
            OverviewWidgetItem(
                overviewWidgetType = OverviewWidgetType.APPROVAL_RATE,
                overviewType = OverviewType.ONLINE_PAYMENTS,
                title = MR.strings.approval_rate
            ),
            OverviewWidgetItem(
                overviewWidgetType = OverviewWidgetType.APPROVAL_RATE,
                overviewType = OverviewType.POS_PAYMENTS,
                title = MR.strings.approval_rate
            ),
            OverviewWidgetItem(
                overviewWidgetType = OverviewWidgetType.AVERAGE_METRICS,
                overviewType = OverviewType.ONLINE_PAYMENTS,
                title = MR.strings.average_metrics
            ),
            OverviewWidgetItem(
                overviewWidgetType = OverviewWidgetType.AVERAGE_METRICS,
                overviewType = OverviewType.POS_PAYMENTS,
                title = MR.strings.average_metrics
            ),
            OverviewWidgetItem(
                overviewWidgetType = OverviewWidgetType.CHARGED_TRANSACTIONS,
                overviewType = OverviewType.ONLINE_PAYMENTS,
                title = MR.strings.charged_transactions
            ),
            OverviewWidgetItem(
                overviewWidgetType = OverviewWidgetType.ALL_POS_TRANSACTIONS,
                overviewType = OverviewType.POS_PAYMENTS,
                title = MR.strings.all_pos_transactions
            ),
            OverviewWidgetItem(
                overviewWidgetType = OverviewWidgetType.CHARGED_TRANSACTIONS_COMPARISON,
                overviewType = OverviewType.ONLINE_PAYMENTS,
                title = MR.strings.charged_transactions_comparison
            ),
            OverviewWidgetItem(
                overviewWidgetType = OverviewWidgetType.CHARGED_TRANSACTIONS_COMPARISON,
                overviewType = OverviewType.POS_PAYMENTS,
                title = MR.strings.charged_transactions_comparison
            ),
            OverviewWidgetItem(
                overviewWidgetType = OverviewWidgetType.PRODUCT_GUIDE,
                overviewType = OverviewType.ONLINE_PAYMENTS,
                title = MR.strings.charged_transactions_comparison
            ),
            OverviewWidgetItem(
                overviewWidgetType = OverviewWidgetType.PRODUCT_GUIDE,
                overviewType = OverviewType.POS_PAYMENTS,
                title = MR.strings.charged_transactions_comparison
            )
        )
    }
}