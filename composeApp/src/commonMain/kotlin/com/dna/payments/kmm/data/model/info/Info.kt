package com.dna.payments.kmm.data.model.info

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val domainNames: List<String>,
    val encryptTo: String,
    val partnerInternalMerchantIdentifier: String,
    val partnerMerchantName: String,
    val partnerMerchantValidationURI: String
)