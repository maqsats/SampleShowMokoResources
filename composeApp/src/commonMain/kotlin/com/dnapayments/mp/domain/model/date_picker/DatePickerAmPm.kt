package com.dnapayments.mp.domain.model.date_picker

import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.text_switch.TextSwitch
import dev.icerock.moko.resources.StringResource

enum class DatePickerAmPm(
    override val index: Int,
    override val displayName: StringResource
) : TextSwitch {
    AM(0, MR.strings.am_time),
    PM(1, MR.strings.pm_time)
}