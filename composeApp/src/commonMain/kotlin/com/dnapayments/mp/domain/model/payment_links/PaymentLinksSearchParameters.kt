package com.dnapayments.mp.domain.model.payment_links

data class PaymentLinksSearchParameters(
    var startDate: String,
    var endDate: String,
    var status: String,
    var page: Int = 0,
    var size: Int = 0
)