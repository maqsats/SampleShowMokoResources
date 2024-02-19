package com.dnapayments.mp.domain.use_case

import com.dnapayments.mp.domain.model.payment_links.PaymentLinkStatus

interface PaymentLinkStatusUseCase {

    fun getMainPaymentLinkStatus(): List<PaymentLinkStatus>
}