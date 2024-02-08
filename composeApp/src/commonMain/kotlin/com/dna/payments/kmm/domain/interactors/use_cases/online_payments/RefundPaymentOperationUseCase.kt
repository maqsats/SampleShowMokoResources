package com.dna.payments.kmm.domain.interactors.use_cases.online_payments

import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.OnlinePaymentOperationRepository

class RefundPaymentOperationUseCase(
    private val repository: OnlinePaymentOperationRepository
) {
    suspend operator fun invoke(
        transactionId: String,
        amount: Int
    ): Response<Unit> =
        repository.refundPaymentOperation(transactionId, amount)
}