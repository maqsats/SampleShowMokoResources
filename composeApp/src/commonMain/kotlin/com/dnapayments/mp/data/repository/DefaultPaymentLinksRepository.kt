package com.dnapayments.mp.data.repository

import com.dnapayments.mp.data.model.payment_links.PaymentLinkMapper
import com.dnapayments.mp.data.preferences.Preferences
import com.dnapayments.mp.domain.model.payment_links.PaymentLink
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.PaymentLinksRepository
import com.dnapayments.mp.utils.constants.Constants
import com.dnapayments.mp.utils.extension.handleApiCall
import com.dnapayments.mp.utils.extension.toBearerToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url

class DefaultPaymentLinksRepository(
    private val httpClient: HttpClient,
    private val preferences: Preferences,
    private val paymentLinkMapper: PaymentLinkMapper
) : PaymentLinksRepository {

    override suspend fun getPaymentLink(
        startDate: String,
        endDate: String,
        status: String,
        page: Int,
        size: Int
    ): Response<PaymentLink> =
        paymentLinkMapper.map(
            handleApiCall {
                httpClient.get {
                    url("${Constants.BASE_URL}v1/payment-links?sort=created_date&order=desc&from=${startDate}&to=${endDate}&status=${status}&page=${page}&size=${size}")
                    header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                }.body()
            }
        )
}