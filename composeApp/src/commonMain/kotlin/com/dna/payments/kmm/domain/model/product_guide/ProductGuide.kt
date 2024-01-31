package com.dna.payments.kmm.domain.model.product_guide

import androidx.compose.runtime.Immutable
import dev.icerock.moko.resources.StringResource

@Immutable
data class ProductGuide(
    val imageBackground: String,
    val title: StringResource,
    val description: StringResource,
    val detailLink: StringResource
)
