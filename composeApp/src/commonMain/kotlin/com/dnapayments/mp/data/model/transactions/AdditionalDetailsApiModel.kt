package com.dnapayments.mp.data.model.transactions

import kotlinx.serialization.Serializable

@Serializable
data class AdditionalDetailsApiModel(
    val paymentStatus: String,
    val refundSupported: Boolean
)