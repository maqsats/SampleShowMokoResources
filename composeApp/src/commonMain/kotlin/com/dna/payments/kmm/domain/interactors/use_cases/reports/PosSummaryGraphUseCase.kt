package com.dna.payments.kmm.domain.interactors.use_cases.reports

import com.dna.payments.kmm.data.model.overview.SummaryPosRequest
import com.dna.payments.kmm.domain.model.date_picker.IntervalType
import com.dna.payments.kmm.domain.model.reports.HistogramEntry
import com.dna.payments.kmm.domain.network.Response

interface PosSummaryGraphUseCase {

    suspend fun getPosSummaryGraph(
        summaryPosRequest: SummaryPosRequest, intervalType: IntervalType
    ): Response<HistogramEntry>
}