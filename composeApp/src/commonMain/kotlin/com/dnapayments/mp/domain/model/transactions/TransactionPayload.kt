package com.dnapayments.mp.domain.model.transactions

data class TransactionPayload(
    val records: List<Transaction>?,
    val totalCount: Int
)