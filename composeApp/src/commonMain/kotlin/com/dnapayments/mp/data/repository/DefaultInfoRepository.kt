package com.dnapayments.mp.data.repository

import com.dnapayments.mp.data.model.info.Info
import com.dnapayments.mp.data.preferences.Preferences
import com.dnapayments.mp.domain.model.payment_methods.setting.PaymentMethodType
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.InfoRepository
import com.dnapayments.mp.utils.constants.Constants
import com.dnapayments.mp.utils.extension.handleApiCall
import com.dnapayments.mp.utils.extension.toBearerToken
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