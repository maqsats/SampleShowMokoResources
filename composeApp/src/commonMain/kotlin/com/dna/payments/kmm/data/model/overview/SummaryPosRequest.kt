package com.dna.payments.kmm.data.model.overview

import kotlinx.serialization.Serializable

@Serializable
data class SummaryPosRequest(
    val from: String,
    val to: String,
    val interval: String,
    val currency: String,
    val status: String,
)