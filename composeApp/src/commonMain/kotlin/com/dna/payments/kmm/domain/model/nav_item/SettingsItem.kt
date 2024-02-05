package com.dna.payments.kmm.domain.model.nav_item

import androidx.compose.runtime.Immutable
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

@Immutable
data class SettingsItem(
    val imageDrawableId: ImageResource,
    val title: StringResource,
    val position: SettingsPosition,
)
