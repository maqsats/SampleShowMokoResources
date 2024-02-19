package com.dnapayments.mp.domain.repository

import com.dnapayments.mp.domain.model.payment_links.PaymentLink
import com.dnapayments.mp.domain.network.Response

interface PaymentLinksRepository {

    suspend fun getPaymentLink(
        startDate: String,
        endDate: String,
        status: String,
        page: Int,
        size: Int
    ): Response<PaymentLink>
}