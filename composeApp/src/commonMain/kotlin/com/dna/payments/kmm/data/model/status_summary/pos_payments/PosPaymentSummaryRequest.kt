package com.dna.payments.kmm.data.model.status_summary.pos_payments

import kotlinx.serialization.Serializable

@Serializable
data class PosPaymentSummaryRequest(
    val from: String,
    val to: String,
    val currency: String
)