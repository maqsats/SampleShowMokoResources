package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.data.model.overview.SummaryListApiModel
import com.dna.payments.kmm.data.model.overview.SummaryOnlinePaymentsApiModel
import com.dna.payments.kmm.data.model.overview.SummaryPosRequest
import com.dna.payments.kmm.data.model.report.ReportApiModel
import com.dna.payments.kmm.data.model.report.ReportRequest
import com.dna.payments.kmm.data.model.search.Search
import com.dna.payments.kmm.data.model.transactions.pos.PosRequestParam
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentSummary
import com.dna.payments.kmm.domain.model.status_summary.Summary
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

    suspend fun getIssuer(
        reportRequest: ReportRequest
    ): Response<ReportApiModel>

    suspend fun getCardScheme(
        reportRequest: ReportRequest
    ): Response<ReportApiModel>

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
    ): Response<List<PosPaymentSummary>>
}