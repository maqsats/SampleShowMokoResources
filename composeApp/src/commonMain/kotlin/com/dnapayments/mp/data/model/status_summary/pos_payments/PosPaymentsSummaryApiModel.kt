package com.dnapayments.mp.data.model.status_summary.pos_payments

import kotlinx.serialization.Serializable

@Serializable
data class PosPaymentsSummaryApiModel(
    val amount: Double,
    val count: Int,
    val currency: String
)