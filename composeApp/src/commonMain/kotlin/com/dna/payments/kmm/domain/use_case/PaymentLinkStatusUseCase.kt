package com.dna.payments.kmm.domain.use_case

import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkStatus

interface PaymentLinkStatusUseCase {

    fun getMainPaymentLinkStatus(): List<PaymentLinkStatus>
}