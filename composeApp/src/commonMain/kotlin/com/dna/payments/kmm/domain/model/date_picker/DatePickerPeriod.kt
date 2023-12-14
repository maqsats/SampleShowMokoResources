package com.dna.payments.kmm.domain.model.date_picker

import com.dna.payments.kmm.MR
import dev.icerock.moko.resources.StringResource

enum class DatePickerPeriod(val stringResource: StringResource) {
    TODAY(MR.strings.today),
    YESTERDAY(MR.strings.yesterday),
    THIS_WEEK(MR.strings.this_week),
    LAST_WEEK(MR.strings.last_week),
    PREVIOUS_WEEK(MR.strings.previous_week),
    CURRENT_MONTH(MR.strings.current_month),
    LAST_30_DAYS(MR.strings.last_30_days),
    LAST_MONTH(MR.strings.last_month),
    LAST_60_DAYS(MR.strings.last_60_days),
    LAST_90_DAYS(MR.strings.last_90_days),
    THIS_YEAR(MR.strings.this_year),
    LAST_12_MONTHS(MR.strings.last_12_months)
}