package com.dnapayments.mp.domain.repository

import com.dnapayments.mp.data.model.info.Info
import com.dnapayments.mp.domain.model.payment_methods.setting.PaymentMethodType
import com.dnapayments.mp.domain.network.Response

interface InfoRepository {
    suspend fun getInfo(paymentMethodType: PaymentMethodType): Response<Info>
}