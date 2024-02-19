package com.dnapayments.mp.data.repository

import com.dnapayments.mp.data.model.overview.SummaryListApiModel
import com.dnapayments.mp.data.model.overview.SummaryOnlinePaymentsApiModel
import com.dnapayments.mp.data.model.overview.SummaryPosRequest
import com.dnapayments.mp.data.model.report.ReportItem
import com.dnapayments.mp.data.model.report.ReportOnlinePaymentsRequest
import com.dnapayments.mp.data.model.report.ReportPosPaymentsApiModel
import com.dnapayments.mp.data.model.report.ReportPosPaymentsRequest
import com.dnapayments.mp.data.model.search.Search
import com.dnapayments.mp.data.model.status_summary.StatusSummaryMapper
import com.dnapayments.mp.data.model.status_summary.pos_payments.PosPaymentSummaryRequest
import com.dnapayments.mp.data.model.status_summary.pos_payments.PosPaymentsStatusSummaryMapper
import com.dnapayments.mp.data.model.transactions.TransactionPayLoadMapper
import com.dnapayments.mp.data.model.transactions.pos.PosRequestParam
import com.dnapayments.mp.data.model.transactions.pos.PosTransactionMapper
import com.dnapayments.mp.data.preferences.Preferences
import com.dnapayments.mp.domain.model.overview_report.Summary
import com.dnapayments.mp.domain.model.transactions.TransactionPayload
import com.dnapayments.mp.domain.model.transactions.pos.PosTransactions
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.TransactionRepository
import com.dnapayments.mp.utils.constants.Constants
import com.dnapayments.mp.utils.extension.handleApiCall
import com.dnapayments.mp.utils.extension.toBearerToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

class DefaultTransactionRepository(
    private val httpClient: HttpClient,
    private val preferences: Preferences,
    private val statusSummaryMapper: StatusSummaryMapper,
    private val transactionPayLoadMapper: TransactionPayLoadMapper,
    private val posPaymentsStatusSummaryMapper: PosPaymentsStatusSummaryMapper,
    private val posTransactionMapper: PosTransactionMapper
) : TransactionRepository {

    override suspend fun getStatusSummary(
        startDate: String,
        endDate: String,
        currency: String
    ): Response<List<Summary>> =
        statusSummaryMapper.map(
            handleApiCall {
                httpClient.get {
                    url("${Constants.BASE_URL}v1/ecom/transactions/status-summary?from=${startDate}&to=${endDate}&currency=${currency}")
                    header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                }.body()
            })

    override suspend fun getPosGraphSummary(
        summaryPosRequest: SummaryPosRequest
    ): Response<SummaryListApiModel> =
        handleApiCall {
            httpClient.post {
                url("${Constants.BASE_URL}v1/pos/transactions/summary")
                header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(summaryPosRequest)
            }.body()
        }

    override suspend fun getPosPaymentIssuer(
        reportPosPaymentsRequest: ReportPosPaymentsRequest
    ): Response<ReportPosPaymentsApiModel> =
        handleApiCall {
            httpClient.post {
                url("${Constants.BASE_URL}v1/pos/transactions/top-summary/issuer")
                header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(reportPosPaymentsRequest)
            }.body()
        }

    override suspend fun getOnlinePaymentIssuer(request: ReportOnlinePaymentsRequest): Response<List<ReportItem>> {
        return handleApiCall {
            httpClient.get {
                url("${Constants.BASE_URL}v1/ecom/transactions/top-summary/issuer")
                header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                parameter("from", request.from)
                parameter("to", request.to)
                parameter("currency", request.currency)
                parameter("orderBy", request.orderBy)
                parameter("status", request.status)
                parameter("top", request.top)
            }.body()
        }
    }

    override suspend fun getPosPaymentsCardScheme(
        reportPosPaymentsRequest: ReportPosPaymentsRequest
    ): Response<ReportPosPaymentsApiModel> =
        handleApiCall {
            httpClient.post {
                url("${Constants.BASE_URL}v1/pos/transactions/top-summary/card-scheme")
                header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(reportPosPaymentsRequest)
            }.body()
        }

    override suspend fun getOnlinePaymentsCardScheme(request: ReportOnlinePaymentsRequest): Response<List<ReportItem>> {
        return handleApiCall {
            httpClient.get {
                url("${Constants.BASE_URL}v1/ecom/transactions/top-summary/card-scheme")
                header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                parameter("from", request.from)
                parameter("to", request.to)
                parameter("currency", request.currency)
                parameter("orderBy", request.orderBy)
                parameter("status", request.status)
                parameter("top", request.top)
            }.body()
        }
    }

    override suspend fun getOnlinePaymentMethods(request: ReportOnlinePaymentsRequest): Response<List<ReportItem>> {
        return handleApiCall {
            httpClient.get {
                url("${Constants.BASE_URL}v1/ecom/transactions/top-summary/payment-method")
                header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                parameter("from", request.from)
                parameter("to", request.to)
                parameter("currency", request.currency)
                parameter("orderBy", request.orderBy)
                parameter("status", request.status)
                parameter("top", request.top)
            }.body()
        }
    }

    override suspend fun getOnlineGraphSummary(
        startDate: String,
        endDate: String,
        currency: String,
        interval: String,
        status: String
    ): Response<List<SummaryOnlinePaymentsApiModel>> =
        handleApiCall {
            httpClient.get {
                url("${Constants.BASE_URL}v1/ecom/transactions/summary?from=${startDate}&to=${endDate}&currency=${currency}&interval=${interval}&status=${status}")
                header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
            }.body()
        }

    override suspend fun getPosPaymentStatusSummary(
        startDate: String,
        endDate: String,
        currency: String
    ): Response<List<Summary>> =
        posPaymentsStatusSummaryMapper.map(
            handleApiCall {
                httpClient.post {
                    url("${Constants.BASE_URL}v1/pos/transactions/total-summary")
                    header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                    setBody(PosPaymentSummaryRequest(startDate, endDate, currency))
                }.body()
            })

    override suspend fun getTransactionBySearchParameter(search: Search): Response<TransactionPayload> =
        transactionPayLoadMapper.map(
            handleApiCall {
                httpClient.post {
                    url("${Constants.BASE_URL}v1/ecom/transactions")
                    header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                    setBody(search)
                }.body()
            })

    override suspend fun getPosPaymentTransactions(
        posRequestParam: PosRequestParam
    ): Response<PosTransactions> =
        posTransactionMapper.map(
            handleApiCall {
                httpClient.post {
                    url("${Constants.BASE_URL}v2/pos/transactions")
                    header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                    setBody(posRequestParam)
                }.body()
            })
}
