package com.dnapayments.mp.domain.interactors.use_cases.online_payments

import com.dnapayments.mp.data.model.online_payments.RefundResult
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.OnlinePaymentOperationRepository

class RefundPaymentOperationUseCase(
    private val repository: OnlinePaymentOperationRepository
) {
    suspend operator fun invoke(
        transactionId: String,
        amount: Double
    ): Response<RefundResult> =
        repository.refundPaymentOperation(transactionId, amount)
}