package com.dna.payments.kmm.domain.model.nav_item

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

data class SettingsItem(
    val imageDrawableId: ImageResource,
    val title: StringResource,
    val position: SettingsPosition,
)
