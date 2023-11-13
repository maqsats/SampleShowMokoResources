package com.dna.payments.kmm.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val code: Int,
    val message: String
)