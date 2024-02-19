package com.dnapayments.mp.domain.repository

import com.dnapayments.mp.data.model.payment_methods.NewTerminalStatusRequest
import com.dnapayments.mp.domain.model.payment_methods.setting.PaymentMethodType
import com.dnapayments.mp.domain.network.Response

interface SettingRepository {
    suspend fun changingTerminalStatus(
        newTerminalStatusRequest: NewTerminalStatusRequest,
        paymentMethodTypeUrl: PaymentMethodType
    ): Response<Unit>
}