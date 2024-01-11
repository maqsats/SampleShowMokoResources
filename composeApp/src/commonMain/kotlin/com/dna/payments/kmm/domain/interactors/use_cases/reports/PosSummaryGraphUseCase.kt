package com.dna.payments.kmm.domain.interactors.use_cases.reports

import com.dna.payments.kmm.data.model.overview.SummaryPosRequest
import com.dna.payments.kmm.domain.model.date_picker.IntervalType
import com.dna.payments.kmm.domain.model.reports.HistogramEntry
import com.dna.payments.kmm.domain.network.Response
import com.soywiz.klock.DateTime

interface PosSummaryGraphUseCase {

    suspend fun getPosSummaryGraph(
        startDate: DateTime?,
        endDate: DateTime?,
        currency: String,
        intervalType: IntervalType
    ): Response<Pair<HistogramEntry, HistogramEntry>>
}