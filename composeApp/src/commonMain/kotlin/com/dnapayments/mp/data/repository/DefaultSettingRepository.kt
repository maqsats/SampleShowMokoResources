package com.dnapayments.mp.data.repository

import com.dnapayments.mp.data.model.payment_methods.NewTerminalStatusRequest
import com.dnapayments.mp.data.preferences.Preferences
import com.dnapayments.mp.domain.model.payment_methods.setting.PaymentMethodType
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.SettingRepository
import com.dnapayments.mp.utils.constants.Constants
import com.dnapayments.mp.utils.extension.handleApiCall
import com.dnapayments.mp.utils.extension.toBearerToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.patch
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