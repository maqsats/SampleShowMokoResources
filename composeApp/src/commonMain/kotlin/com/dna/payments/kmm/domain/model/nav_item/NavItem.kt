package com.dna.payments.kmm.domain.model.nav_item

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

data class NavItem(
    val imageDrawableId: ImageResource,
    val title: StringResource,
    val position: NavItemPosition,
    var isSelected: Boolean = false,
    val childTitle: String = "",
    val isAvailable: Boolean = true
)
