package com.dna.payments.kmm.domain.model.overview

import dev.icerock.moko.resources.StringResource

data class OverviewWidgetItem(
    val overviewWidgetType: OverviewWidgetType,
    val overviewType: OverviewType,
    val title: StringResource,
    val hint: StringResource? = null
)
