package com.dna.payments.kmm.domain.interactors.use_cases.reports.approval_average_metrics

import com.dna.payments.kmm.domain.model.average_metrics.Metric
import com.dna.payments.kmm.domain.model.online_payments.OnlinePaymentStatus
import com.dna.payments.kmm.domain.model.overview_report.Summary
import com.dna.payments.kmm.domain.network.Response
import com.soywiz.klock.DateTime

interface GetReportUseCase {

    suspend fun getOnlinePaymentsSummary(
        startDate: DateTime?,
        endDate: DateTime?,
        currency: String
    ): Response<Pair<List<Summary>, List<Metric>>>

    suspend fun getPosPaymentsSummary(
        startDate: DateTime?,
        endDate: DateTime?,
        currency: String
    ): Response<Pair<List<Summary>, List<Metric>>>

    fun getOrderedPaymentStatus(): List<OnlinePaymentStatus>
}