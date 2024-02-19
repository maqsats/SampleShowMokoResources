package com.dnapayments.mp.data.model.report

import kotlinx.serialization.Serializable

@Serializable
data class ReportItem(
    val amount: Double,
    val count: Int,
    val currency: String,
    val value: String
)