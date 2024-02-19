package com.dnapayments.mp.data.model.transactions.pos

import kotlinx.serialization.Serializable

@Serializable
data class PosTransactionApiModel(
    val amount: Double?,
    val captureMethod: String?,
    val cardMask: String?,
    val cardScheme: String?,
    val cardType: String?,
    val currency: String?,
    val isCorporateCard: Boolean?,
    val isEuropeanCard: Boolean?,
    val issuerRegion: String?,
    val mid: String?,
    val operation: String?,
    val returnCode: String?,
    val returnCodeDescription: String?,
    val status: String?,
    val terminalId: String?,
    val transactionCity: String?,
    val transactionCountry: String?,
    val transactionDate: String?,
    val transactionDetails: String?,
    val transactionId: String?,
    val transactionType: String?
)