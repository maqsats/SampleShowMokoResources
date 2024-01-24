package com.dna.payments.kmm.domain.model.text_switch

import com.dna.payments.kmm.MR
import dev.icerock.moko.resources.StringResource

enum class DnaOrderByType(
    override val index: Int,
    override val displayName: StringResource,
    val key: String
) : TextSwitch {
    AMOUNT(
        0,
        MR.strings.amount,
        "amount"
    ),
    COUNT(
        1,
        MR.strings.count,
        "count"
    )
}