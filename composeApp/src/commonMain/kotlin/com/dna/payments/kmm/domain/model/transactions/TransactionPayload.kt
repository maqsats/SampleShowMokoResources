package com.dna.payments.kmm.domain.model.transactions

data class TransactionPayload(
    val records: List<Transaction>?,
    val totalCount: Int
)