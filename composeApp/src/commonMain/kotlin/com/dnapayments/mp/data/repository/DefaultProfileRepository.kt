package com.dnapayments.mp.data.repository

import com.dnapayments.mp.data.model.profile.Profile
import com.dnapayments.mp.data.preferences.Preferences
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.ProfileRepository
import com.dnapayments.mp.utils.constants.Constants
import com.dnapayments.mp.utils.extension.handleApiCall
import com.dnapayments.mp.utils.extension.toBearerToken
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