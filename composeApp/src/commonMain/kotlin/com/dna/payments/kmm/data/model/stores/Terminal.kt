package com.dna.payments.kmm.data.model.stores

import com.dna.payments.kmm.data.model.stores.Settings

data class Terminal(
    val defaultCurrency: String,
    val id: String,
    val isActive: Boolean,
    val isDefault: Boolean,
    val settings: Settings,
    val supportedCurrencies: List<String>
)