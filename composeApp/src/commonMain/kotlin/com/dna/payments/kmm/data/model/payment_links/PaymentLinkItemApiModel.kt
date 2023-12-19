package com.dna.payments.kmm.data.model.payment_links

import kotlinx.serialization.Serializable

@Serializable
data class PaymentLinkItemApiModel(
    val amount: Double,
    val createdDate: String,
    val currency: String,
    val customerName: String,
    val description: String,
    val expirationDate: String,
    val id: String,
    val initiatorEmail: String,
    val invoiceId: String,
    val paidDate: String?,
    val status: String,
    val url: String,
    val viewedDate: String?
)