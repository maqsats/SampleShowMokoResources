package com.dnapayments.mp.domain.model.payment_links

data class PaymentLink(
    val records: List<PaymentLinkItem>,
    val totalCount: Int
)