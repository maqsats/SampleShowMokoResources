package com.dna.payments.kmm.data.model.team_management

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeammateApiModel(
    val createdAt: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val login: String,
    val merchantId: String,
    @SerialName("permissions")
    val permissionList: List<String>,
    val roles: List<String>,
    val status: String,
    val type: String
)