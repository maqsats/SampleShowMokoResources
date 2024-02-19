package com.dnapayments.mp.domain.repository

import com.dnapayments.mp.data.model.payment_methods.RegistrationDomainRequest
import com.dnapayments.mp.data.model.payment_methods.UnregisterDomainRequest
import com.dnapayments.mp.domain.model.payment_methods.setting.PaymentMethodType
import com.dnapayments.mp.domain.network.Response

interface DomainsRepository {
    suspend fun unregisterDomain(
        unregisterDomainRequest: UnregisterDomainRequest,
        paymentMethodTypeUrl: PaymentMethodType
    ): Response<Unit>

    suspend fun registerDomain(
        registerDomainRequest: RegistrationDomainRequest,
        paymentMethodTypeUrl: PaymentMethodType
    ): Response<Unit>
}