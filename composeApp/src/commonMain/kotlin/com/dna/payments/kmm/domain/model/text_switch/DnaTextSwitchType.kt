package com.dna.payments.kmm.domain.model.text_switch

import com.dna.payments.kmm.MR
import dev.icerock.moko.resources.StringResource

enum class DnaTextSwitchType(override val index: Int, override val displayName: StringResource) :
    TextSwitch {
    AMOUNT(
        0,
        MR.strings.amount,
    ),
    COUNT(
        1,
        MR.strings.count
    )
}