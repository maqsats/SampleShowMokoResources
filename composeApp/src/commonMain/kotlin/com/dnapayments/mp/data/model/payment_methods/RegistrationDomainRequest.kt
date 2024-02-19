package com.dnapayments.mp.data.model.payment_methods

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationDomainRequest(
    val domainNames: List<String>
)
