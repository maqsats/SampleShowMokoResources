package com.dna.payments.kmm.data.model.request

data class EmailVerificationRequest(
    val email: String,
    val key: String
)