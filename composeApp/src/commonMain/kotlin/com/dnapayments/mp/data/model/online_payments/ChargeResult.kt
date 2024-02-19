package com.dnapayments.mp.data.model.online_payments

import kotlinx.serialization.Serializable

@Serializable
data class ChargeResult(
    val id: String,
    val code: String? = null,
    val message: String? = null,
)