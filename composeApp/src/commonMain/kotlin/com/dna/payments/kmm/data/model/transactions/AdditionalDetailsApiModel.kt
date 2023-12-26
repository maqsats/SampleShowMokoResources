package com.dna.payments.kmm.data.model.transactions

import kotlinx.serialization.Serializable

@Serializable
data class AdditionalDetailsApiModel(
    val paymentStatus: String,
    val refundSupported: Boolean
)