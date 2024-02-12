package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.data.model.online_payments.ChargeResult
import com.dna.payments.kmm.data.model.online_payments.RefundResult
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
        amount: Double
    ): Response<ChargeResult>

    suspend fun refundPaymentOperation(
        transactionId: String,
        amount: Double
    ): Response<RefundResult>

    suspend fun processNewPaymentOperation(
        processNewPaymentRequest: ProcessNewPaymentRequest
    ): Response<Unit>

}