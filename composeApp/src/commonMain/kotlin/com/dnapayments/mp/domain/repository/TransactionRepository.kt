package com.dnapayments.mp.domain.repository

import com.dnapayments.mp.data.model.overview.SummaryListApiModel
import com.dnapayments.mp.data.model.overview.SummaryOnlinePaymentsApiModel
import com.dnapayments.mp.data.model.overview.SummaryPosRequest
import com.dnapayments.mp.data.model.report.ReportItem
import com.dnapayments.mp.data.model.report.ReportOnlinePaymentsRequest
import com.dnapayments.mp.data.model.report.ReportPosPaymentsApiModel
import com.dnapayments.mp.data.model.report.ReportPosPaymentsRequest
import com.dnapayments.mp.data.model.search.Search
import com.dnapayments.mp.data.model.transactions.pos.PosRequestParam
import com.dnapayments.mp.domain.model.overview_report.Summary
import com.dnapayments.mp.domain.model.transactions.TransactionPayload
import com.dnapayments.mp.domain.model.transactions.pos.PosTransactions
import com.dnapayments.mp.domain.network.Response

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