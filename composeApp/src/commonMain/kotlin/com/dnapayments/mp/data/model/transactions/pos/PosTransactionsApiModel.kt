package com.dnapayments.mp.data.model.transactions.pos

import kotlinx.serialization.Serializable

@Serializable
data class PosTransactionsApiModel(
    val data: List<PosTransactionApiModel>?,
    val totalAmount: Double?,
    val totalCount: Int?
)