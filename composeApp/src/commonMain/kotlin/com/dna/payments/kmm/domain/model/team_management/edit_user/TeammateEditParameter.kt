package com.dna.payments.domain.presentation.team_management.edit_user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeammateEditParam(
    val firstName: String,
    val lastName: String,
    val id: String,
    val email: String,
    val roles: List<String>,
    val permissionList: List<String>
) : Parcelable