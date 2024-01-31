package com.dna.payments.kmm.data.model.status_summary

import com.dna.payments.kmm.domain.model.map.Mapper
import com.dna.payments.kmm.domain.model.online_payments.OnlinePaymentStatus
import com.dna.payments.kmm.domain.model.overview_report.Summary


class StatusSummaryMapper : Mapper<StatusSummaryApiModel, List<Summary>>() {

    override fun mapData(from: StatusSummaryApiModel): List<Summary> {
        return from.data.map {
            mapItem(it)
        }
    }

    private fun mapItem(from: SummaryApiModel): Summary {
        val onlinePaymentStatus = OnlinePaymentStatus.fromString(from.status)
        return Summary(
            from.amount,
            from.count,
            onlinePaymentStatus
        )
    }
}