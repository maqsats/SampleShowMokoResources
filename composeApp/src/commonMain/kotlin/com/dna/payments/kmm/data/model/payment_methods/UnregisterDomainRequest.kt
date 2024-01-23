package com.dna.payments.kmm.data.model.payment_methods

import kotlinx.serialization.Serializable

@Serializable
data class UnregisterDomainRequest(
    val domainNames: List<String>,
    val reason: String
)