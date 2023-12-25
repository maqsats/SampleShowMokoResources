package com.dna.payments.kmm.domain.interactors.use_cases.transaction

import com.dna.payments.kmm.domain.model.average_metrics.Metric
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentSummary
import com.dna.payments.kmm.domain.model.status_summary.PaymentStatus
import com.dna.payments.kmm.domain.model.status_summary.Summary
import com.dna.payments.kmm.domain.network.Response
import com.soywiz.klock.DateTime

interface TransactionUseCase {

    suspend fun getMainSummary(
        startDate: DateTime?,
        endDate: DateTime?,
        currency: String
    ): Response<List<Summary>>

    suspend fun getPosPaymentsSummary(
        startDate: DateTime?,
        endDate: DateTime?,
        currency: String
    ): Response<List<PosPaymentSummary>>

    fun getAverageMetricsOnlinePayments(summaryList: List<Summary>, daysCount: Int?): List<Metric>

    fun getAverageMetricsPosPayments(
        summaryList: List<PosPaymentSummary>,
        daysCount: Int?
    ): List<Metric>

    fun getOrderedPaymentStatus(): List<PaymentStatus>

}