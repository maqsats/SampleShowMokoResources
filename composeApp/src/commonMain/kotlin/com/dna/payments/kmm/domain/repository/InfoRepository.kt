package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.data.model.info.Info
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType
import com.dna.payments.kmm.domain.network.Response

interface InfoRepository {
    suspend fun getInfo(paymentMethodType: PaymentMethodType): Response<Info>
}