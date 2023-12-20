package com.dna.payments.kmm.data.model.status_summary

data class StatusSummaryApiModel(
    val currency: String,
    val data: List<SummaryApiModel>
)