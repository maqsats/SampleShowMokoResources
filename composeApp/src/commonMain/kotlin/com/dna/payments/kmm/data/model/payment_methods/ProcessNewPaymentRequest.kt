package com.dna.payments.kmm.data.model.payment_methods

import kotlinx.serialization.Serializable

@Serializable
data class ProcessNewPaymentRequest(
    val amount: Int,
    val invoiceId: String,
    val merchantCustomData: String?,
    val parentTransactionId: String,
    val periodicType: String = "ucof",
    val sequenceType: String = "recurring",
    val transactionType: String
)