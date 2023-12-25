package com.dna.payments.kmm.data.model.overview

data class SummaryOnlinePaymentsApiModel(
    val amount: Double = 0.0,
    val count: Int = 0,
    val currency: String = "",
    val date: String = "2020-01-01"
)