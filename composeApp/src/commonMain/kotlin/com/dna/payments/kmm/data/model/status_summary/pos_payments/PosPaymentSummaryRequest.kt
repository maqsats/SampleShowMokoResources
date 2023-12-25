package com.dna.payments.kmm.data.model.status_summary.pos_payments

data class PosPaymentSummaryRequest(
    val from: String,
    val to: String,
    val currency: String
)