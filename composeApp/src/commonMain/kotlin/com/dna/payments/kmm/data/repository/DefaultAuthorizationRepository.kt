package com.dna.payments.kmm.data.repository

import com.dna.payments.kmm.data.model.authorization.AuthToken
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.AuthorizationRepository
import com.dna.payments.kmm.utils.constants.Constants
import com.dna.payments.kmm.utils.extension.handleApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.http.Parameters

class DefaultAuthorizationRepository(
    private val httpClient: HttpClient,
) : AuthorizationRepository {

    override suspend fun getUserToken(username: String, password: String): Response<AuthToken> =
        handleApiCall {
            httpClient.submitForm(
                url = "${Constants.BASE_AUTH_URL}oauth2/token",
                formParameters = Parameters.build {
                    append("grant_type", Constants.GRANT_TYPE)
                    append("scope", Constants.SCOPE)
                    append("username", username)
                    append("password", password)
                    append("client_id", Constants.CLIENT_ID)
                    append("client_secret", Constants.CLIENT_SECRET)
                }
            )
        }

    override suspend fun updateToken(refreshToken: String): Response<AuthToken> =
        handleApiCall {
            httpClient.submitForm(
                url = "${Constants.BASE_AUTH_URL}oauth2/token",
                formParameters = Parameters.build {
                    append("grant_type", Constants.GRANT_TYPE)
                    append("scope", Constants.SCOPE)
                    append("refresh_token", refreshToken)
                    append("client_id", Constants.CLIENT_ID)
                    append("client_secret", Constants.CLIENT_SECRET)
                }
            ).body()
        }

    override suspend fun changeMerchant(merchantId: String): Response<AuthToken> =
        handleApiCall {
            httpClient.submitForm(
                url = "${Constants.BASE_AUTH_URL}oauth2/token",
                formParameters = Parameters.build {
                    append("grant_type", Constants.GRANT_TYPE)
                    append("merchantId", merchantId)
                }
            ).body()
        }
}