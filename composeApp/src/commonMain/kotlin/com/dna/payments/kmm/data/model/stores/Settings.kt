package com.dna.payments.kmm.data.model.stores

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val applepay: Setting,
    val ecospend: Setting,
    val googlepay: Setting,
    val klarna: Setting,
    val card: Card,
    val payByBankApp: Setting,
    val paypal: Setting
)