package com.dnapayments.mp.data.model.create_new_link

import kotlinx.serialization.Serializable

@Serializable
data class CreateNewLinkApiModel(
    val amount: Double,
    val createdDate: String,
    val currency: String,
    val customerName: String,
    val description: String,
    val expirationDate: String,
    val id: String,
    val initiatorEmail: String?,
    val invoiceId: String,
    val status: String,
    val terminalId: String,
    val url: String
)