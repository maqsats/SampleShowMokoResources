package com.dna.payments.domain.presentation.team_management

data class TeamManagementSearchParameters(
    var role: String,
    var isActive: Boolean,
    var page: Int = 0,
    var size: Int = 0,
)