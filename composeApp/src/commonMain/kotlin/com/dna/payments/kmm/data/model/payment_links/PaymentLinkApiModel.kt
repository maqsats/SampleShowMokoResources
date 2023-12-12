package com.dna.payments.kmm.data.model.payment_links

import kotlinx.serialization.Serializable

@Serializable
data class PaymentLinkApiModel(
    val records: List<PaymentLinkItemApiModel>,
    val totalCount: Int
)