package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.data.model.payment_methods.NewTerminalStatusRequest
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType
import com.dna.payments.kmm.domain.network.Response

interface SettingRepository {
    suspend fun changingTerminalStatus(
        newTerminalStatusRequest: NewTerminalStatusRequest,
        paymentMethodTypeUrl: PaymentMethodType
    ): Response<Unit>
}