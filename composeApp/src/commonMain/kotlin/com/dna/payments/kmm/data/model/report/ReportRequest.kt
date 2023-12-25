package com.dna.payments.kmm.data.model.report

data class ReportRequest(
    val from: String,
    val to: String,
    val currency: String,
    val orderBy: String,
    val top: Int = 5
)