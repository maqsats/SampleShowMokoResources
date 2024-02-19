package com.dnapayments.mp.domain.interactors.use_cases.reports.approval_average_metrics

import com.dnapayments.mp.domain.model.average_metrics.Metric
import com.dnapayments.mp.domain.model.online_payments.OnlinePaymentStatus
import com.dnapayments.mp.domain.model.overview_report.Summary
import com.dnapayments.mp.domain.network.Response
import korlibs.time.DateTime

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