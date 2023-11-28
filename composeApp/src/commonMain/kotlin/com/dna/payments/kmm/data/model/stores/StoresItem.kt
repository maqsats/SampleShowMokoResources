package com.dna.payments.kmm.data.model.stores

data class StoresItem(
    val id: String,
    val isActive: Boolean,
    val mid: String,
    val name: String,
    val terminals: List<Terminal>,
    val type: String
)