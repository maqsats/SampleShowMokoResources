package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.data.model.payment_methods.ProcessNewPaymentRequest
import com.dna.payments.kmm.data.model.payment_methods.SendReceiptRequest
import com.dna.payments.kmm.domain.network.Response

interface OnlinePaymentOperationRepository {
    suspend fun cancelPendingOperation(
        transactionId: String
    ): Response<Unit>

    suspend fun sendReceiptOperation(
        transactionId: String,
        sendReceiptRequest: SendReceiptRequest
    ): Response<Unit>

    suspend fun chargePaymentOperation(
        transactionId: String,
        amount: Int
    ): Response<Unit>

    suspend fun refundPaymentOperation(
        transactionId: String,
        amount: Int
    ): Response<Unit>

    suspend fun processNewPaymentOperation(
        processNewPaymentRequest: ProcessNewPaymentRequest
    ): Response<Unit>

}