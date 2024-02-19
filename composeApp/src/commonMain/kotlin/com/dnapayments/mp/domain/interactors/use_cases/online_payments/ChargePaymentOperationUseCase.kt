package com.dnapayments.mp.domain.interactors.use_cases.online_payments

import com.dnapayments.mp.data.model.online_payments.ChargeResult
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.OnlinePaymentOperationRepository

class ChargePaymentOperationUseCase(
    private val repository: OnlinePaymentOperationRepository
) {
    suspend operator fun invoke(
        transactionId: String,
        amount: Double
    ): Response<ChargeResult> =
        repository.chargePaymentOperation(transactionId, amount)
}