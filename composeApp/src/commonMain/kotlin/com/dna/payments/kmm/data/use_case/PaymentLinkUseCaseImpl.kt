package com.dna.payments.kmm.data.use_case

import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkStatus
import com.dna.payments.kmm.domain.use_case.PaymentLinkStatusUseCase

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