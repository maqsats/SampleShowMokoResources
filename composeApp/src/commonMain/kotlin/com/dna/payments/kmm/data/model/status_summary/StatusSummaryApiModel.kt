package com.dna.payments.kmm.data.model.status_summary

import kotlinx.serialization.Serializable

@Serializable
data class StatusSummaryApiModel(
    val currency: String,
    val data: List<SummaryApiModel>
)