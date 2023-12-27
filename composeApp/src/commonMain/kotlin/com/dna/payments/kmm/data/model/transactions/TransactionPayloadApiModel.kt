package com.dna.payments.kmm.data.model.transactions

import kotlinx.serialization.Serializable

@Serializable
data class TransactionPayloadApiModel(
    val records: List<TransactionApiModel>?,
    val totalCount: Int
)