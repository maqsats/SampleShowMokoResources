package com.dna.payments.kmm.domain.interactors.use_cases.reports

import com.dna.payments.kmm.domain.model.date_picker.IntervalType
import com.dna.payments.kmm.domain.model.reports.HistogramEntry
import com.dna.payments.kmm.domain.network.Response

interface OnlineSummaryGraphUseCase {

    suspend fun getOnlineSummaryGraph(
        startDate: String,
        endDate: String,
        currency: String,
        interval: String,
        status: String,
        intervalType: IntervalType
    ): Response<HistogramEntry>
}