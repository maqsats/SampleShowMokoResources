package com.dnapayments.mp.domain.interactors.use_cases.payment_method

import com.dnapayments.mp.data.model.payment_methods.UnregisterDomainRequest
import com.dnapayments.mp.domain.model.payment_methods.setting.PaymentMethodType
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.DomainsRepository

class UnregisterDomainUseCase(
    private val domainsRepository: DomainsRepository
) {
    suspend operator fun invoke(
        paymentMethodType: PaymentMethodType,
        unregisterDomainRequest: UnregisterDomainRequest
    ): Response<Unit> =
        domainsRepository.unregisterDomain(unregisterDomainRequest, paymentMethodType)
}