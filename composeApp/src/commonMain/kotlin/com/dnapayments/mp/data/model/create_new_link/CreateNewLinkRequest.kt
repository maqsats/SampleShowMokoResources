package com.dnapayments.mp.data.model.create_new_link

import kotlinx.serialization.Serializable

@Serializable
data class CreateNewLinkRequest(
    val amount: Double,
    val currency: String,
    val customerName: String,
    val description: String,
    val expirationDate: String,
    val invoiceId: String,
    val terminalId: String,
    val periodic: Periodic?,
    val transactionType: String?
)