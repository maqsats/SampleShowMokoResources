package com.dna.payments.kmm.data.model.stores

import com.dna.payments.kmm.data.model.stores.Setting

data class Settings(
    val applepay: Setting,
    val card: Setting,
    val ecospend: Setting,
    val googlepay: Setting,
    val klarna: Setting,
    val payByBankApp: Setting,
    val paypal: Setting
)