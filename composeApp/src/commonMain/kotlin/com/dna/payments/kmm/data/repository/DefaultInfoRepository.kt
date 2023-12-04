package com.dna.payments.kmm.data.repository

import com.dna.payments.kmm.data.model.info.Info
import com.dna.payments.kmm.data.preferences.Preferences
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.InfoRepository
import com.dna.payments.kmm.utils.constants.Constants
import com.dna.payments.kmm.utils.extension.handleApiCall
import com.dna.payments.kmm.utils.extension.toBearerToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url

class DefaultInfoRepository(
    private val httpClient: HttpClient,
    private val preferences: Preferences
) : InfoRepository {
    override suspend fun getInfo(paymentMethodType: PaymentMethodType): Response<Info> =
        handleApiCall {
            httpClient.get {
                url("${Constants.BASE_URL}v1/${paymentMethodType.url}/merchants/info")
                header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
            }.body()
        }
}