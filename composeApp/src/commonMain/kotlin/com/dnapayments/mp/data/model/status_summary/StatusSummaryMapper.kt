package com.dnapayments.mp.data.model.status_summary

import com.dnapayments.mp.domain.model.map.Mapper
import com.dnapayments.mp.domain.model.online_payments.OnlinePaymentStatus
import com.dnapayments.mp.domain.model.overview_report.Summary


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