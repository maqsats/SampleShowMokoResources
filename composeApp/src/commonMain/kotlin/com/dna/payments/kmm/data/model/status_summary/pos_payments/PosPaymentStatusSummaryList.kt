package com.dna.payments.kmm.data.model.status_summary.pos_payments

data class PosPaymentStatusSummaryList(
    val all: List<PosPaymentsSummaryApiModel>,
    val failed: List<PosPaymentsSummaryApiModel>,
    val successful: List<PosPaymentsSummaryApiModel>
)