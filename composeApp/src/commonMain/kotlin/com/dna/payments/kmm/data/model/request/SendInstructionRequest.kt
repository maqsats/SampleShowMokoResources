package com.dna.payments.kmm.data.model.request

import kotlinx.serialization.Serializable


@Serializable
data class SendInstructionRequest(
    val email: String,
)