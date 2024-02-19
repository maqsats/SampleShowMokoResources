package com.dnapayments.mp.data.model.stores

data class Paypal(
    val defaultTransactionType: String,
    val lastUpdateDate: String,
    val paypalAccountMerchantId: String,
    val shippingPreference: String,
    val status: String
)