package com.dna.payments.kmm.data.model.team_management

import com.dna.payments.kmm.domain.model.team_management.TeamManagement
import com.dna.payments.kmm.domain.model.team_management.Teammate
import com.dna.payments.kmm.domain.model.team_management.UserStatus
import com.dna.payments.kmm.domain.model.map.Mapper


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
                systemRoles = it.systemRoles,
                type = it.type,
                id = it.id,
                permissionList = it.permissionList
            )
        }
    }
}