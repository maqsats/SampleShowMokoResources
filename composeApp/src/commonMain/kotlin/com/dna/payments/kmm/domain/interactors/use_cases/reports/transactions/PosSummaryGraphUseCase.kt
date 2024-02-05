package com.dna.payments.kmm.domain.interactors.use_cases.reports.transactions

import com.dna.payments.kmm.domain.model.date_picker.IntervalType
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentStatus
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.utils.chart.histogram.HistogramEntry
import korlibs.time.DateTime

interface PosSummaryGraphUseCase {

    suspend fun getPosSummaryGraph(
        startDate: DateTime?,
        endDate: DateTime?,
        currency: String,
        intervalType: IntervalType,
        status: PosPaymentStatus
    ): Response<Pair<HistogramEntry, HistogramEntry>>
}