package com.dna.payments.kmm.domain.interactors.use_cases.reports.transactions

import com.dna.payments.kmm.domain.model.date_picker.IntervalType
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.utils.chart.histogram.HistogramEntry
import com.soywiz.klock.DateTime

interface OnlineSummaryGraphUseCase {

    suspend fun getOnlineSummaryGraph(
        startDate: DateTime?,
        endDate: DateTime?,
        currency: String,
        intervalType: IntervalType,
        status: String
    ): Response<Pair<HistogramEntry, HistogramEntry>>
}