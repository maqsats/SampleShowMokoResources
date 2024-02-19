package com.dnapayments.mp.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmailVerification(
    @SerialName("Id")
    val id: String
)