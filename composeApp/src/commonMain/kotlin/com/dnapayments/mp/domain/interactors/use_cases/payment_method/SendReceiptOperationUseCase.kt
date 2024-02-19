package com.dnapayments.mp.domain.interactors.use_cases.payment_method

import com.dnapayments.mp.data.model.payment_methods.SendReceiptRequest
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.OnlinePaymentOperationRepository

class SendReceiptOperationUseCase(
    private val repository: OnlinePaymentOperationRepository
) {
    suspend operator fun invoke(
        transactionId: String,
        sendReceiptRequest: SendReceiptRequest
    ): Response<Unit> =
        repository.sendReceiptOperation(transactionId, sendReceiptRequest)
}