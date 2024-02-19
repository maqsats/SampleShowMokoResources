package com.dnapayments.mp.data.repository

import com.dnapayments.mp.data.model.authorization.AuthToken
import com.dnapayments.mp.data.preferences.Preferences
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.AuthorizationRepository
import com.dnapayments.mp.utils.constants.Constants
import com.dnapayments.mp.utils.extension.handleApiCall
import com.dnapayments.mp.utils.extension.toBearerToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.Parameters

class DefaultAuthorizationRepository(
    private val httpClient: HttpClient,
    private val preferences: Preferences
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
                    append("grant_type", Constants.GRANT_TYPE_REFRESH_TOKEN)
                    append("scope", Constants.SCOPE)
                    append("refresh_token", refreshToken)
                    append("client_id", Constants.CLIENT_ID)
                    append("client_secret", Constants.CLIENT_SECRET)
                },
            ).body()
        }

    override suspend fun changeMerchant(merchantId: String): Response<AuthToken> =
        handleApiCall {
            httpClient.put {
                url("${Constants.BASE_AUTH_URL}oauth2/token")
                header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                setBody(
                    FormDataContent(
                        Parameters.build {
                            append("grant_type", Constants.GRANT_TYPE)
                            append("merchant_id", merchantId)
                        })
                )
            }.body()
        }
}