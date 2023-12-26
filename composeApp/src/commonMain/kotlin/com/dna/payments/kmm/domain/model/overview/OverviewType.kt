package com.dna.payments.kmm.domain.model.overview

import com.dna.payments.kmm.MR
import dev.icerock.moko.resources.StringResource

enum class OverviewType(val pageId: Int, val displayName: StringResource) {
    POS_PAYMENTS(0, MR.strings.pos_payments),
    ONLINE_PAYMENTS
        (1, MR.strings.online_payments)
}