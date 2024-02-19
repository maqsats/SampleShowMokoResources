package com.dnapayments.mp.domain.model.pincode

import com.dnapayments.mp.MR
import dev.icerock.moko.resources.StringResource

enum class PinState(val stringResourceId: StringResource) {
    CREATE(MR.strings.create_pin_code),
    ENTER(MR.strings.enter_pin_code),
    REPEAT(MR.strings.repeat_pin_code),
    SUCCESS(MR.strings.enter_pin_code),
    ERROR(MR.strings.code_not_same),
    ERROR_REPEAT(MR.strings.code_not_correct)
}