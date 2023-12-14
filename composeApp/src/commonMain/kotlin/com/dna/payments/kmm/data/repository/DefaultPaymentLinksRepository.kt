package com.dna.payments.kmm.data.repository

import com.dna.payments.kmm.data.model.payment_links.PaymentLinkMapper
import com.dna.payments.kmm.data.preferences.Preferences
import com.dna.payments.kmm.domain.model.payment_links.PaymentLink
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.PaymentLinksRepository
import com.dna.payments.kmm.utils.constants.Constants
import com.dna.payments.kmm.utils.extension.handleApiCall
import com.dna.payments.kmm.utils.extension.toBearerToken
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