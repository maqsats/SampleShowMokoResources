package com.dnapayments.mp.domain.model.text_switch

import dev.icerock.moko.resources.StringResource

interface TextSwitch {
    val index: Int
    val displayName: StringResource
}