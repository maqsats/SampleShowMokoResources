package com.dna.payments.kmm.data.repository

import com.dna.payments.kmm.data.model.team_management.TeamManagementMapper
import com.dna.payments.kmm.data.preferences.Preferences
import com.dna.payments.kmm.domain.model.team_management.TeamManagement
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.TeamManagementRepository
import com.dna.payments.kmm.utils.constants.Constants
import com.dna.payments.kmm.utils.extension.handleApiCall
import com.dna.payments.kmm.utils.extension.toBearerToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url

class DefaultTeamManagementRepository(
    private val httpClient: HttpClient,
    private val preferences: Preferences,
    private val teamManagementMapper: TeamManagementMapper
) : TeamManagementRepository {

    override suspend fun getTeamManagement(
        role: String,
        isActive: Boolean,
        page: Int,
        size: Int
    ): Response<TeamManagement> = teamManagementMapper.map(
        handleApiCall {
            httpClient.get {
                url("${Constants.BASE_URL}v1/teammates?role=${role}&active=${isActive}&page=${page}&size=${size}")
                header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
            }.body()
        })
}