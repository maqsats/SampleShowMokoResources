package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.domain.model.team_management.TeamManagement
import com.dna.payments.kmm.domain.network.Response

interface TeamManagementRepository {

    suspend fun getTeamManagement(
        role: String,
        isActive: Boolean,
        page: Int,
        size: Int,
    ): Response<TeamManagement>
}