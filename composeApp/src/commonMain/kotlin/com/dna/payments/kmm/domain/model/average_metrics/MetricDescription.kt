package com.dna.payments.kmm.domain.model.average_metrics

import com.dna.payments.kmm.MR
import dev.icerock.moko.resources.StringResource

enum class MetricDescription(val stringResource: StringResource) {
    SUCCESSFUL_AVERAGE(MR.strings.successful_average),
    SUCCESSFUL_DAILY_AVERAGE(MR.strings.successful_average_daily),
    SUCCESSFUL_DAILY_AVERAGE_NUMBER(MR.strings.successful_average_daily_number)
}