package com.dnapayments.mp.domain.model.transactions.pos

data class PosTransactions(
    val data: List<PosTransaction>,
    val totalAmount: Double,
    val totalCount: Int
)