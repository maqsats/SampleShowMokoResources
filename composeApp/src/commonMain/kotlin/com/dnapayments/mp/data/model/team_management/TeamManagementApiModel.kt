package com.dnapayments.mp.data.model.team_management

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamManagementApiModel(
    @SerialName("teamMates")
    val teammateList: List<TeammateApiModel>,
    val totalCount: Int
)