package com.dna.payments.kmm.domain.model.product_guide

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

data class ProductGuide(
    val imageBackground: ImageResource,
    val title: StringResource,
    val description: StringResource,
    val detailLink: StringResource
)
