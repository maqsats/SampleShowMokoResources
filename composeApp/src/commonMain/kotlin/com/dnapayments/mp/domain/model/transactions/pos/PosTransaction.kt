package com.dnapayments.mp.domain.model.transactions.pos

import com.dnapayments.mp.domain.model.online_payments.TransactionType
import com.dnapayments.mp.domain.model.pos_payments.PosPaymentStatusV2
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
data class PosTransaction(
    val amount: Double,
    var captureMethod: String,
    val cardMask: String,
    val cardScheme: String,
    val cardType: String,
    val currency: String,
    val isCorporateCard: Boolean?,
    val isEuropeanCard: Boolean,
    val issuerRegion: String?,
    val mid: String,
    val operation: String,
    val returnCode: String,
    val returnCodeDescription: String,
    val status: PosPaymentStatusV2,
    val terminalId: String,
    val transactionCity: String,
    val transactionCountry: String,
    val transactionDate: String,
    val transactionDetails: String,
    val transactionId: String,
    val transactionType: TransactionType
) : Parcelable