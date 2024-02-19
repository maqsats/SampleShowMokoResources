package com.dnapayments.mp.data.model.stores

import kotlinx.serialization.Serializable

@Serializable
data class StoresItem(
    val id: String,
    val isActive: Boolean,
    val mid: String,
    val name: String,
    val terminals: List<Terminal>,
    val type: String
) {
    override fun toString(): String {
        return "StoresItem(id='$id', isActive=$isActive, mid='$mid', name='$name', terminals=$terminals, type='$type')"
    }
}