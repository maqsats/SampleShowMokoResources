package com.dnapayments.mp.data.use_case

import com.dnapayments.mp.domain.model.payment_links.PaymentLinkStatus
import com.dnapayments.mp.domain.use_case.PaymentLinkStatusUseCase

class PaymentLinkStatusUseCaseImpl : PaymentLinkStatusUseCase {

    override fun getMainPaymentLinkStatus(): List<PaymentLinkStatus> = listOf(
        PaymentLinkStatus.ALL,
        PaymentLinkStatus.ACTIVE,
        PaymentLinkStatus.EXPIRED,
        PaymentLinkStatus.PAID,
        PaymentLinkStatus.CANCELLED,
        PaymentLinkStatus.ATTEMPTED,
        PaymentLinkStatus.VIEWED
    )
}