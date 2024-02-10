package com.dna.payments.kmm.domain.model.date_picker

import com.dna.payments.kmm.MR
import dev.icerock.moko.resources.StringResource
import korlibs.time.DateTime

sealed class DatePickerPeriod {
    abstract val stringResource: StringResource

    data class TODAY(override val stringResource: StringResource = MR.strings.today) :
        DatePickerPeriod()

    data class YESTERDAY(override val stringResource: StringResource = MR.strings.yesterday) :
        DatePickerPeriod()

    data class THIS_WEEK(override val stringResource: StringResource = MR.strings.this_week) :
        DatePickerPeriod()

    data class LAST_WEEK(override val stringResource: StringResource = MR.strings.last_week) :
        DatePickerPeriod()

    data class PREVIOUS_WEEK(override val stringResource: StringResource = MR.strings.previous_week) :
        DatePickerPeriod()

    data class CURRENT_MONTH(override val stringResource: StringResource = MR.strings.current_month) :
        DatePickerPeriod()

    data class LAST_30_DAYS(override val stringResource: StringResource = MR.strings.last_30_days) :
        DatePickerPeriod()

    data class LAST_MONTH(override val stringResource: StringResource = MR.strings.last_month) :
        DatePickerPeriod()

    data class LAST_60_DAYS(override val stringResource: StringResource = MR.strings.last_60_days) :
        DatePickerPeriod()

    data class LAST_90_DAYS(override val stringResource: StringResource = MR.strings.last_90_days) :
        DatePickerPeriod()

    data class THIS_YEAR(override val stringResource: StringResource = MR.strings.this_year) :
        DatePickerPeriod()

    data class LAST_12_MONTHS(override val stringResource: StringResource = MR.strings.last_12_months) :
        DatePickerPeriod()

    data class CUSTOM(
        override val stringResource: StringResource = MR.strings.custom,
        val startDate: DateTime,
        val endDate: DateTime
    ) : DatePickerPeriod()
}