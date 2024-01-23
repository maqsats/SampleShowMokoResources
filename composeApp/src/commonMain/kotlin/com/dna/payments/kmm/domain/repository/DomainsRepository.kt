package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.data.model.payment_methods.RegistrationDomainRequest
import com.dna.payments.kmm.data.model.payment_methods.UnregisterDomainRequest
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType
import com.dna.payments.kmm.domain.network.Response

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