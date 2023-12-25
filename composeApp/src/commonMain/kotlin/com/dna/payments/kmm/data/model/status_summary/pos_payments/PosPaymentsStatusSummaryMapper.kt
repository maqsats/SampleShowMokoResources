package com.dna.payments.kmm.data.model.status_summary.pos_payments

import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.ZERO_DOUBLE_VALUE
import com.dna.payments.kmm.domain.model.map.Mapper
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentStatus
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentSummary


class PosPaymentsStatusSummaryMapper :
    Mapper<PosPaymentStatusSummaryList, List<PosPaymentSummary>>() {

    override fun mapData(from: PosPaymentStatusSummaryList): List<PosPaymentSummary> {
        return listOf(
            PosPaymentSummary(
                if (from.all.isEmpty()) ZERO_DOUBLE_VALUE else from.all.first().amount,
                if (from.all.isEmpty()) 0 else from.all.first().count,
                PosPaymentStatus.ALL
            ),
            PosPaymentSummary(
                if (from.successful.isEmpty()) ZERO_DOUBLE_VALUE else from.successful.first().amount,
                if (from.successful.isEmpty()) 0 else from.successful.first().count,
                PosPaymentStatus.CHARGE
            ),
            PosPaymentSummary(
                if (from.failed.isEmpty()) ZERO_DOUBLE_VALUE else from.failed.first().amount,
                if (from.failed.isEmpty()) 0 else from.failed.first().count,
                PosPaymentStatus.REJECT
            )
        )
    }
}