package com.dnapayments.mp.data.model.status_summary.pos_payments

import com.dnapayments.mp.domain.interactors.use_cases.date_picker.DatePickerConstants.ZERO_DOUBLE_VALUE
import com.dnapayments.mp.domain.model.map.Mapper
import com.dnapayments.mp.domain.model.overview_report.Summary
import com.dnapayments.mp.domain.model.pos_payments.PosPaymentStatus


class PosPaymentsStatusSummaryMapper :
    Mapper<PosPaymentStatusSummaryList, List<Summary>>() {

    override fun mapData(from: PosPaymentStatusSummaryList): List<Summary> {
        return listOf(
            Summary(
                if (from.all.isEmpty()) ZERO_DOUBLE_VALUE else from.all.first().amount,
                if (from.all.isEmpty()) 0 else from.all.first().count,
                PosPaymentStatus.ALL
            ),
            Summary(
                if (from.successful.isEmpty()) ZERO_DOUBLE_VALUE else from.successful.first().amount,
                if (from.successful.isEmpty()) 0 else from.successful.first().count,
                PosPaymentStatus.CHARGE
            ),
            Summary(
                if (from.failed.isEmpty()) ZERO_DOUBLE_VALUE else from.failed.first().amount,
                if (from.failed.isEmpty()) 0 else from.failed.first().count,
                PosPaymentStatus.REJECT
            )
        )
    }
}