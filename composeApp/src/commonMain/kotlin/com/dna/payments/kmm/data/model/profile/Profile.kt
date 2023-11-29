package com.dna.payments.kmm.data.model.profile

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val acquisitionChannel: String,
    val createdAt: String,
    val email: String,
    val id: String,
    val merchants: List<Merchant>,
    val portalGuideViewedDate: String,
    val publicId: String,
    val timezone: String,
    val updatedAt: String
)