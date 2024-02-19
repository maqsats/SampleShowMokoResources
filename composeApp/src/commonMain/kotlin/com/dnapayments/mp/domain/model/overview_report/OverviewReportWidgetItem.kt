package com.dnapayments.mp.domain.model.overview_report

import androidx.compose.runtime.Immutable
import dev.icerock.moko.resources.StringResource

@Immutable
data class OverviewReportWidgetItem(
    val overviewReportWidgetType: OverviewReportWidgetType,
    val overviewReportType: OverviewReportType,
    val title: StringResource,
    val hint: StringResource? = null
)
