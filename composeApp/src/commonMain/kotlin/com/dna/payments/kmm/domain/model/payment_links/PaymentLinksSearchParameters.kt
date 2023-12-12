package com.dna.payments.kmm.domain.model.payment_links

data class PaymentLinksSearchParameters(
    var startDate: String,
    var endDate: String,
    var status: String,
    var page: Int,
    var size: Int
)