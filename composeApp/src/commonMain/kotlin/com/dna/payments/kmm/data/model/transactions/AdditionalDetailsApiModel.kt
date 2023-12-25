package com.dna.payments.kmm.data.model.transactions

data class AdditionalDetailsApiModel(
    val paymentStatus: String,
    val refundSupported: Boolean
)