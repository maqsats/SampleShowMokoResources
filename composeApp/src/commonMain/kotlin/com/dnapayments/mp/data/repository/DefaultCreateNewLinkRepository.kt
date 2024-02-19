package com.dnapayments.mp.data.repository

import com.dnapayments.mp.data.model.create_new_link.CreateNewLinkMapper
import com.dnapayments.mp.data.model.create_new_link.CreateNewLinkRequest
import com.dnapayments.mp.data.preferences.Preferences
import com.dnapayments.mp.domain.model.create_new_link.CreateNewLinkData
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.CreateNewLinkRepository
import com.dnapayments.mp.utils.constants.Constants
import com.dnapayments.mp.utils.extension.handleApiCall
import com.dnapayments.mp.utils.extension.toBearerToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

class DefaultCreateNewLinkRepository(
    private val httpClient: HttpClient,
    private val preferences: Preferences,
    private val createNewLinkMapper: CreateNewLinkMapper
) : CreateNewLinkRepository {

    override suspend fun createNewLink(createNewLinkRequest: CreateNewLinkRequest): Response<CreateNewLinkData> =
        createNewLinkMapper.map(
            handleApiCall {
                httpClient.post {
                    url("${Constants.BASE_URL}v1/payment-links")
                    header(Constants.CREDENTIALS_HEADER, preferences.getAuthToken().toBearerToken())
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                    setBody(createNewLinkRequest)
                }.body()
            }
        )
}