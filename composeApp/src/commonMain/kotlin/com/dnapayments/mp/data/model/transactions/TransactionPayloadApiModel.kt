package com.dnapayments.mp.data.model.transactions

import kotlinx.serialization.Serializable

@Serializable
data class TransactionPayloadApiModel(
    val records: List<TransactionApiModel>?,
    val totalCount: Int
)