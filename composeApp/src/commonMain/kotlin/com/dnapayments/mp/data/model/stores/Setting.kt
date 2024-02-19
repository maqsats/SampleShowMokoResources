package com.dnapayments.mp.data.model.stores

import kotlinx.serialization.Serializable

@Serializable
data class Setting(
    val lastUpdateDate: String?,
    val status: String?
)