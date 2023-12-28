package com.dna.payments.kmm.data.model.transactions.pos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PosRequestParam(
    @SerialName("from")
    val startDate: String,
    var page: Int,
    var size: Int,
    val status: String? = null,
    @SerialName("to")
    val endDate: String
)
