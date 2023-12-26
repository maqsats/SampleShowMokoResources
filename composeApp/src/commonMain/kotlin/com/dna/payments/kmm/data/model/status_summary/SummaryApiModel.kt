package com.dna.payments.kmm.data.model.status_summary

import kotlinx.serialization.Serializable

@Serializable
data class SummaryApiModel(
    val amount: Double,
    val count: Int,
    val status: String
)