package com.dna.payments.kmm.data.repository

import com.dna.payments.kmm.data.model.profile.Profile
import com.dna.payments.kmm.data.preferences.Preferences
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.ProfileRepository
import com.dna.payments.kmm.utils.constants.Constants
import com.dna.payments.kmm.utils.extension.handleApiCall
import com.dna.payments.kmm.utils.extension.toBearerToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url

class DefaultProfileRepository(
    private val httpClient: HttpClient,
    private val preferences: Preferences
) : ProfileRepository {

    override suspend fun getProfile(): Response<Profile> =
        handleApiCall {
            httpClient.get {
                url("${Constants.BASE_URL}profile")
                header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
            }.body()
        }
}