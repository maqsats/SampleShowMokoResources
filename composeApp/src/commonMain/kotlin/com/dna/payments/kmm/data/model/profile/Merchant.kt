package com.dna.payments.kmm.data.model.profile

import kotlinx.serialization.Serializable

@Serializable
data class Merchant(
    val companyName: String,
    val isActive: Boolean,
    val isDefault: Boolean,
    val merchantId: String,
    val name: String,
    val portalGuideViewedDate: String
)