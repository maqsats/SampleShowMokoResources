package com.dna.payments.kmm.data.model.payment_methods

data class UnregisterDomainRequest(
    val domainNames: List<String>,
    val reason: String
)