package com.dnapayments.mp.data.model.stores

import kotlinx.serialization.Serializable

@Serializable
data class Terminal(
    val defaultCurrency: String,
    val id: String,
    val isActive: Boolean,
    val isDefault: Boolean,
    val settings: Settings,
    val supportedCurrencies: List<String>
)