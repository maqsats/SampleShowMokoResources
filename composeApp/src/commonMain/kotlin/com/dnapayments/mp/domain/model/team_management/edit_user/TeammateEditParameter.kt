package com.dnapayments.mp.domain.model.team_management.edit_user

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize


@Parcelize
data class TeammateEditParam(
    val firstName: String,
    val lastName: String,
    val id: String,
    val email: String,
    val roles: List<String>,
    val permissionList: List<String>
) : Parcelable