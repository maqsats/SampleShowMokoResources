package com.dna.payments.kmm.data.repository

import com.dna.payments.kmm.data.model.payment_methods.NewTerminalStatusRequest
import com.dna.payments.kmm.data.preferences.Preferences
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.SettingRepository
import com.dna.payments.kmm.utils.constants.Constants
import com.dna.payments.kmm.utils.extension.handleApiCall
import com.dna.payments.kmm.utils.extension.toBearerToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

class DefaultSettingRepository(
    private val httpClient: HttpClient,
    private val preferences: Preferences,
) : SettingRepository {
    override suspend fun changingTerminalStatus(
        newTerminalStatusRequest: NewTerminalStatusRequest,
        paymentMethodTypeUrl: PaymentMethodType
    ): Response<Unit> =
        handleApiCall {
            httpClient.patch {
                url("${Constants.BASE_URL}v1/${paymentMethodTypeUrl.url}/settings")
                header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(newTerminalStatusRequest)
            }.body()
        }
}