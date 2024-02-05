package com.dna.payments.kmm.domain.model.nav_item

import androidx.compose.runtime.Immutable
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

@Immutable
data class NavItem(
    val imageDrawableId: ImageResource,
    val title: StringResource,
    val position: NavItemPosition,
    val isAvailable: Boolean = true
)
