package com.dna.payments.kmm.data.model.status_summary.pos_payments

data class PosPaymentsSummaryApiModel(
    val amount: Double,
    val count: Int,
    val currency: String
)