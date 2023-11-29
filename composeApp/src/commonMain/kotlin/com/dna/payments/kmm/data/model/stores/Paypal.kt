package com.dna.payments.kmm.data.model.stores

data class Paypal(
    val defaultTransactionType: String,
    val lastUpdateDate: String,
    val paypalAccountMerchantId: String,
    val shippingPreference: String,
    val status: String
)