package com.dna.payments.kmm.domain.interactors.use_cases.payment_method

import com.dna.payments.kmm.data.model.payment_methods.UnregisterDomainRequest
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.DomainsRepository

class UnregisterDomainUseCase(
    private val domainsRepository: DomainsRepository
) {
    suspend operator fun invoke(
        paymentMethodType: PaymentMethodType,
        unregisterDomainRequest: UnregisterDomainRequest
    ): Response<Unit> =
        domainsRepository.unregisterDomain(unregisterDomainRequest, paymentMethodType)
}