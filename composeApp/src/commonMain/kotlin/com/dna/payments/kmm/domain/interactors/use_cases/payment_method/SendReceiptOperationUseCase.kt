package com.dna.payments.kmm.domain.interactors.use_cases.payment_method

import com.dna.payments.kmm.data.model.payment_methods.SendReceiptRequest
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.OnlinePaymentOperationRepository

class SendReceiptOperationUseCase(
    private val repository: OnlinePaymentOperationRepository
) {
    suspend operator fun invoke(
        transactionId: String,
        sendReceiptRequest: SendReceiptRequest
    ): Response<Unit> =
        repository.sendReceiptOperation(transactionId, sendReceiptRequest)
}