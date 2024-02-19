package com.dnapayments.mp.domain.model.team_management

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import kotlinx.serialization.SerialName

@Parcelize
data class Teammate(
    val firstName: String,
    val lastName: String,
    val id: String,
    val email: String,
    val merchantId: String,
    val roles: List<String>,
    val status: UserStatus,
    @SerialName("permissions")
    val permissionList: List<String>,
    val type: String,
    val isUserAdmin: Boolean = true
) : Parcelable