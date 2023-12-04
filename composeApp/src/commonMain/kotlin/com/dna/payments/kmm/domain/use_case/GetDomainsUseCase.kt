package com.dna.payments.kmm.domain.use_case

import com.dna.payments.kmm.data.model.info.Info
import com.dna.payments.kmm.domain.model.payment_methods.domain.Domain
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.InfoRepository

class GetDomainsUseCase(
    private val infoRepository: InfoRepository
) {
    suspend operator fun invoke(paymentMethodType: PaymentMethodType): Response<List<Domain>> =
        when (val response = infoRepository.getInfo(paymentMethodType)) {
            is Response.Error -> Response.Error(response.error)
            is Response.NetworkError -> Response.NetworkError
            is Response.TokenExpire -> Response.TokenExpire
            is Response.Success -> Response.Success(
                getDomains(
                    response.data,
                    paymentMethodType
                )
            )
        }

    private fun getDomains(
        info: Info,
        paymentMethodType: PaymentMethodType
    ): List<Domain> {
        return info.domainNames.map { Domain(name = it, paymentMethodType = paymentMethodType) }
    }
}