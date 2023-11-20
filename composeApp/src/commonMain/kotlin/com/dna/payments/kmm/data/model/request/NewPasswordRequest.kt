package com.dna.payments.kmm.data.model.request

data class NewPasswordRequest(
    val email: String,
    val password: String,
    val verificationId: String
)