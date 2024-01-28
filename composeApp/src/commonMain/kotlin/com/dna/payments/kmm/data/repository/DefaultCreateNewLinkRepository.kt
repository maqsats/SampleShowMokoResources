package com.dna.payments.kmm.data.repository

import com.dna.payments.kmm.data.model.create_new_link.CreateNewLinkMapper
import com.dna.payments.kmm.data.model.create_new_link.CreateNewLinkRequest
import com.dna.payments.kmm.data.preferences.Preferences
import com.dna.payments.kmm.domain.model.create_new_link.CreateNewLinkData
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.CreateNewLinkRepository
import com.dna.payments.kmm.utils.constants.Constants
import com.dna.payments.kmm.utils.extension.handleApiCall
import com.dna.payments.kmm.utils.extension.toBearerToken
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