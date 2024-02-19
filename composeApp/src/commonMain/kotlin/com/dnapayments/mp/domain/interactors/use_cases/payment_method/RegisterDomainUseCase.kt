package com.dnapayments.mp.domain.interactors.use_cases.payment_method

import com.dnapayments.mp.data.model.payment_methods.RegistrationDomainRequest
import com.dnapayments.mp.domain.model.payment_methods.setting.PaymentMethodType
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.DomainsRepository

class RegisterDomainUseCase(
    private val domainsRepository: DomainsRepository
) {
    suspend operator fun invoke(
        paymentMethodType: PaymentMethodType,
        registerDomainRequest: RegistrationDomainRequest
    ): Response<Unit> =
        domainsRepository.registerDomain(registerDomainRequest, paymentMethodType)
}