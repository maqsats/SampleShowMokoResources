package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.domain.model.payment_links.PaymentLink
import com.dna.payments.kmm.domain.network.Response

interface PaymentLinksRepository {

    suspend fun getPaymentLink(
        startDate: String,
        endDate: String,
        status: String,
        page: Int,
        size: Int
    ): Response<PaymentLink>
}