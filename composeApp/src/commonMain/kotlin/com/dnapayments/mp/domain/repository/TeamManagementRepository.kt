package com.dnapayments.mp.domain.repository

import com.dnapayments.mp.domain.model.team_management.TeamManagement
import com.dnapayments.mp.domain.network.Response

interface TeamManagementRepository {

    suspend fun getTeamManagement(
        role: String,
        isActive: Boolean,
        page: Int,
        size: Int,
    ): Response<TeamManagement>
}