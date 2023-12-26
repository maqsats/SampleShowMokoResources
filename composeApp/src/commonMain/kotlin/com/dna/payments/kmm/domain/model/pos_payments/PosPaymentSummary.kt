package com.dna.payments.kmm.domain.model.pos_payments

import kotlinx.serialization.Serializable

@Serializable
data class PosPaymentSummary(
    val amount: Double,
    val count: Int,
    val status: PosPaymentStatus,
    var percentage: Double = 0.0
)