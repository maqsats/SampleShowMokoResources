package com.dna.payments.kmm.data.model.report

import kotlinx.serialization.Serializable

@Serializable
data class ReportPosPaymentsRequest(
    val from: String,
    val to: String,
    val currency: String,
    val orderBy: String,
    val top: Int = 5
)