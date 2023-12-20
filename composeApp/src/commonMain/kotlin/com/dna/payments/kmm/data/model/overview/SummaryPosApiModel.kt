package com.dna.payments.kmm.data.model.overview

data class SummaryPosApiModel(
    val amount: Double = 0.0,
    val count: Int = 0,
    val currency: String = "",
    val value: String = "2020-01-01"
)