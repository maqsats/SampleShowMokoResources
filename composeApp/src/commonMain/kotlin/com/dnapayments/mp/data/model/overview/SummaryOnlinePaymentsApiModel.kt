package com.dnapayments.mp.data.model.overview

import kotlinx.serialization.Serializable

@Serializable
data class SummaryOnlinePaymentsApiModel(
    val amount: Double = 0.0,
    val count: Int = 0,
    val currency: String = "",
    val date: String = "2020-01-01"
)