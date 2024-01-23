package com.dna.payments.kmm.data.model.payment_methods

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationDomainRequest(
    val domainNames: List<String>
)
