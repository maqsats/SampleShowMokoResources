package com.dnapayments.mp.domain.interactors.use_cases.reports.transactions

import com.dnapayments.mp.domain.model.date_picker.IntervalType
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.utils.chart.histogram.HistogramEntry
import korlibs.time.DateTime

interface OnlineSummaryGraphUseCase {

    suspend fun getOnlineSummaryGraph(
        startDate: DateTime?,
        endDate: DateTime?,
        currency: String,
        intervalType: IntervalType,
        status: String
    ): Response<Pair<HistogramEntry, HistogramEntry>>
}