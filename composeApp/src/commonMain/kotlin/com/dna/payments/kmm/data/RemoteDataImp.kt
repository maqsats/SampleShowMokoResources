package com.dna.payments.kmm.data

import com.dna.payments.kmm.data.model.ApiCharacter
import com.dna.payments.kmm.data.model.ApiCharactersResponse
import com.dna.payments.kmm.data.model.mapper.ApiCharacterMapper
import com.dna.payments.kmm.domain.model.Character
import com.dna.payments.kmm.data.repository.IRemoteData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post

class RemoteDataImp(
    private val endPoint: String,
    private val httpClient: HttpClient,
    private val apiCharacterMapper: ApiCharacterMapper,
) : IRemoteData {
    override suspend fun getCharactersFromApi(): List<Character> =
        apiCharacterMapper.map(
            (httpClient.post("$endPoint/api/character").body<ApiCharactersResponse>()).results
        )

    override suspend fun getCharacterFromApi(id: Int): Character =
        apiCharacterMapper.map(
            httpClient.get("$endPoint/api/character/$id").body<ApiCharacter>()
        )
}