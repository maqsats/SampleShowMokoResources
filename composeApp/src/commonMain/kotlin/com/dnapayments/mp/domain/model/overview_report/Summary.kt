package com.dnapayments.mp.domain.model.overview_report

import com.dnapayments.mp.domain.model.payment_status.PaymentStatus

data class Summary(
    val amount: Double,
    val count: Int,
    val status: PaymentStatus,
    var percentage: Double = 0.0
)