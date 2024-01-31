package com.dna.payments.kmm.data.model.report

data class ReportOnlinePaymentsRequest(
    val from: String,
    val to: String,
    val currency: String,
    val status: String,
    val orderBy: String,
    val top: Int = 5
)