package com.dna.payments.kmm.data.model.profile

data class Merchant(
    val companyName: String,
    val isActive: Boolean,
    val isDefault: Boolean,
    val merchantId: String,
    val name: String,
    val portalGuideViewedDate: String
)