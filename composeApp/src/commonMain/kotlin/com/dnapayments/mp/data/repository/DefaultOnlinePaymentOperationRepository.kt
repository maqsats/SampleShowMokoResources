package com.dnapayments.mp.data.repository

import com.dnapayments.mp.data.model.online_payments.ChargeResult
import com.dnapayments.mp.data.model.online_payments.RefundResult
import com.dnapayments.mp.data.model.payment_methods.ProcessNewPaymentRequest
import com.dnapayments.mp.data.model.payment_methods.SendReceiptRequest
import com.dnapayments.mp.data.preferences.Preferences
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.OnlinePaymentOperationRepository
import com.dnapayments.mp.utils.constants.Constants
import com.dnapayments.mp.utils.extension.handleApiCall
import com.dnapayments.mp.utils.extension.toBearerToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

class DefaultOnlinePaymentOperationRepository(
    private val httpClient: HttpClient,
    private val preferences: Preferences,
) : OnlinePaymentOperationRepository {
    override suspend fun cancelPendingOperation(transactionId: String): Response<Unit> =
        handleApiCall {
            httpClient.post {
                url("${Constants.BASE_URL}operation/$transactionId/cancel")
                header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }.body()
        }

    override suspend fun sendReceiptOperation(
        transactionId: String,
        sendReceiptRequest: SendReceiptRequest
    ): Response<Unit> = handleApiCall {
        httpClient.post {
            url("${Constants.BASE_URL}v1/payments/$transactionId/receipts")
            header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(sendReceiptRequest)
        }.body()
    }

    override suspend fun chargePaymentOperation(
        transactionId: String,
        amount: Double
    ): Response<ChargeResult> = handleApiCall {
        httpClient.post {
            url("${Constants.BASE_URL}operation/$transactionId/charge?amount=${amount}")
            header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }.body()
    }

    override suspend fun refundPaymentOperation(
        transactionId: String,
        amount: Double
    ): Response<RefundResult> = handleApiCall {
        httpClient.post {
            url("${Constants.BASE_URL}operation/$transactionId/refund?amount=${amount}")
            header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }.body()
    }

    override suspend fun processNewPaymentOperation(processNewPaymentRequest: ProcessNewPaymentRequest): Response<Unit> =
        handleApiCall {
            httpClient.post {
                url("${Constants.BASE_URL}transaction/operation/recurring")
                header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(processNewPaymentRequest)
            }.body()
        }
}