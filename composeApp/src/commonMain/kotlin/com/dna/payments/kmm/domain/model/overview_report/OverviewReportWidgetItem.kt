package com.dna.payments.kmm.domain.model.overview_report

import dev.icerock.moko.resources.StringResource

data class OverviewReportWidgetItem(
    val overviewReportWidgetType: OverviewReportWidgetType,
    val overviewReportType: OverviewReportType,
    val title: StringResource,
    val hint: StringResource? = null
)
