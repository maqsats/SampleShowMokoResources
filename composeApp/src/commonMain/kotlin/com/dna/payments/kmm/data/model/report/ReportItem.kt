package com.dna.payments.kmm.data.model.report

data class ReportItem(
    val amount: Double,
    val count: Int,
    val currency: String,
    val value: String
)