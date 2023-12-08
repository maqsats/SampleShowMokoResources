package com.dna.payments.domain.presentation.team_management

import com.dna.payments.kmm.domain.model.team_management.Teammate

data class TeamManagement(
    val teammateList: List<Teammate>,
    val totalCount: Int
)