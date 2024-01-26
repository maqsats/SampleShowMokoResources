package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.data.model.overview.SummaryListApiModel
import com.dna.payments.kmm.data.model.overview.SummaryOnlinePaymentsApiModel
import com.dna.payments.kmm.data.model.overview.SummaryPosRequest
import com.dna.payments.kmm.data.model.report.ReportItem
import com.dna.payments.kmm.data.model.report.ReportOnlinePaymentsRequest
import com.dna.payments.kmm.data.model.report.ReportPosPaymentsApiModel
import com.dna.payments.kmm.data.model.report.ReportPosPaymentsRequest
import com.dna.payments.kmm.data.model.search.Search
import com.dna.payments.kmm.data.model.transactions.pos.PosRequestParam
import com.dna.payments.kmm.domain.model.overview_report.Summary
import com.dna.payments.kmm.domain.model.transactions.TransactionPayload
import com.dna.payments.kmm.domain.model.transactions.pos.PosTransactions
import com.dna.payments.kmm.domain.network.Response

interface TransactionRepository {

    suspend fun getStatusSummary(
        startDate: String,
        endDate: String,
        currency: String
    ): Response<List<Summary>>

    suspend fun getPosGraphSummary(
        summaryPosRequest: SummaryPosRequest
    ): Response<SummaryListApiModel>

    suspend fun getPosPaymentIssuer(
        reportPosPaymentsRequest: ReportPosPaymentsRequest
    ): Response<ReportPosPaymentsApiModel>

    suspend fun getOnlinePaymentIssuer(
        request: ReportOnlinePaymentsRequest
    ): Response<List<ReportItem>>

    suspend fun getPosPaymentsCardScheme(
        reportPosPaymentsRequest: ReportPosPaymentsRequest
    ): Response<ReportPosPaymentsApiModel>

    suspend fun getOnlineGraphSummary(
        startDate: String,
        endDate: String,
        currency: String,
        interval: String,
        status: String
    ): Response<List<SummaryOnlinePaymentsApiModel>>

    suspend fun getTransactionBySearchParameter(
        search: Search
    ): Response<TransactionPayload>

    suspend fun getPosPaymentTransactions(
        posRequestParam: PosRequestParam
    ): Response<PosTransactions>

    suspend fun getPosPaymentStatusSummary(
        startDate: String,
        endDate: String,
        currency: String
    ): Response<List<Summary>>

    suspend fun getOnlinePaymentsCardScheme(request: ReportOnlinePaymentsRequest): Response<List<ReportItem>>
    suspend fun getOnlinePaymentMethods(request: ReportOnlinePaymentsRequest): Response<List<ReportItem>>
}