package com.dnapayments.mp.data.model.team_management

import com.dnapayments.mp.domain.model.team_management.TeamManagement
import com.dnapayments.mp.domain.model.team_management.Teammate
import com.dnapayments.mp.domain.model.team_management.UserStatus
import com.dnapayments.mp.domain.model.map.Mapper


class TeamManagementMapper : Mapper<TeamManagementApiModel, TeamManagement>() {

    override fun mapData(from: TeamManagementApiModel): TeamManagement {
        return TeamManagement(
            mapItem(from.teammateList), from.totalCount
        )
    }

    private fun mapItem(from: List<TeammateApiModel>): List<Teammate> {
        return from.map {
            Teammate(
                firstName = it.firstName,
                lastName = it.lastName,
                email = it.login,
                merchantId = it.merchantId,
                roles = it.roles,
                status = UserStatus.fromString(it.status),
                type = it.type,
                id = it.id,
                permissionList = it.permissionList
            )
        }
    }
}