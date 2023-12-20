package com.dna.payments.kmm.data.model.transactions

data class TransactionPayloadApiModel(
    val records: List<TransactionApiModel>?,
    val totalCount: Int
)