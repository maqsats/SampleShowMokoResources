package com.dnapayments.mp.data.model.status_summary.pos_payments

import kotlinx.serialization.Serializable

@Serializable
data class PosPaymentStatusSummaryList(
    val all: List<PosPaymentsSummaryApiModel>,
    val failed: List<PosPaymentsSummaryApiModel>,
    val successful: List<PosPaymentsSummaryApiModel>
)