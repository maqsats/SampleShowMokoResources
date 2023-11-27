package com.dna.payments.kmm.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class NewPasswordRequest(
    val email: String,
    val password: String,
    val verificationId: String
)