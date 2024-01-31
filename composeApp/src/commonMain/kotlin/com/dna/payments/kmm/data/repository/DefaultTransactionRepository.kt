package com.dna.payments.kmm.data.repository

import com.dna.payments.kmm.data.model.overview.SummaryListApiModel
import com.dna.payments.kmm.data.model.overview.SummaryOnlinePaymentsApiModel
import com.dna.payments.kmm.data.model.overview.SummaryPosRequest
import com.dna.payments.kmm.data.model.report.ReportItem
import com.dna.payments.kmm.data.model.report.ReportOnlinePaymentsRequest
import com.dna.payments.kmm.data.model.report.ReportPosPaymentsApiModel
import com.dna.payments.kmm.data.model.report.ReportPosPaymentsRequest
import com.dna.payments.kmm.data.model.search.Search
import com.dna.payments.kmm.data.model.status_summary.StatusSummaryMapper
import com.dna.payments.kmm.data.model.status_summary.pos_payments.PosPaymentSummaryRequest
import com.dna.payments.kmm.data.model.status_summary.pos_payments.PosPaymentsStatusSummaryMapper
import com.dna.payments.kmm.data.model.transactions.TransactionPayLoadMapper
import com.dna.payments.kmm.data.model.transactions.pos.PosRequestParam
import com.dna.payments.kmm.data.model.transactions.pos.PosTransactionMapper
import com.dna.payments.kmm.data.preferences.Preferences
import com.dna.payments.kmm.domain.model.overview_report.Summary
import com.dna.payments.kmm.domain.model.transactions.TransactionPayload
import com.dna.payments.kmm.domain.model.transactions.pos.PosTransactions
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.TransactionRepository
import com.dna.payments.kmm.utils.constants.Constants
import com.dna.payments.kmm.utils.extension.handleApiCall
import com.dna.payments.kmm.utils.extension.toBearerToken
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
