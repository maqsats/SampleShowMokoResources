package com.dna.payments.kmm.data.model.search

import kotlinx.serialization.Serializable

@Serializable
data class OrderParameter(
    val field: String,
    val typeOrder: String
)