package com.dna.payments.kmm.data.model.status_summary

import com.dna.payments.kmm.domain.model.map.Mapper
import com.dna.payments.kmm.domain.model.status_summary.PaymentStatus
import com.dna.payments.kmm.domain.model.status_summary.Summary


class StatusSummaryMapper : Mapper<StatusSummaryApiModel, List<Summary>>() {

    override fun mapData(from: StatusSummaryApiModel): List<Summary> {
        return from.data.mapNotNull {
            mapItem(it)
        }
    }

    private fun mapItem(from: SummaryApiModel): Summary? {
        val paymentStatus = PaymentStatus.fromString(from.status) ?: return null
        return Summary(
            from.amount,
            from.count,
            paymentStatus
        )
    }
}