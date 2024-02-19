package com.dnapayments.mp.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class EmailVerificationRequest(
    val email: String,
    val key: String
)