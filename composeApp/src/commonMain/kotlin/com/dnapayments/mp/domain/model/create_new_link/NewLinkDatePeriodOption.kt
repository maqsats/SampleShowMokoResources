package com.dnapayments.mp.domain.model.create_new_link

import com.dnapayments.mp.MR
import dev.icerock.moko.resources.StringResource

enum class NewLinkDatePeriodOption(val stringResource: StringResource) {
    ONE_WEEK(MR.strings.one_week),
    ONE_MONTH(MR.strings.one_month),
    FORTY_EIGHT_HOURS(MR.strings.forty_eight_hours),
    TWENTY_FOUR_HOURS(MR.strings.twenty_four_hours),
    CUSTOM(MR.strings.empty_text)
}