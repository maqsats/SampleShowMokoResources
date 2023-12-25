package com.dna.payments.kmm.data.model.status_summary

data class SummaryApiModel(
    val amount: Double,
    val count: Int,
    val status: String
)