package daniel.avila.rnm.kmm.data

import daniel.avila.rnm.kmm.data.model.ApiCharacter
import daniel.avila.rnm.kmm.data.model.ApiCharactersResponse
import daniel.avila.rnm.kmm.data.model.mapper.ApiCharacterMapper
import daniel.avila.rnm.kmm.domain.model.Character
import daniel.avila.rnm.kmm.data.repository.IRemoteData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RemoteDataImp(
    private val endPoint: String,
    private val httpClient: HttpClient,
    private val apiCharacterMapper: ApiCharacterMapper,
) : IRemoteData {
    override suspend fun getCharactersFromApi(): List<Character> =
        apiCharacterMapper.map(
            (httpClient.get("$endPoint/api/character").body<ApiCharactersResponse>()).results
        )

    override suspend fun getCharacterFromApi(id: Int): Character =
        apiCharacterMapper.map(
            httpClient.get("$endPoint/api/character/$id").body<ApiCharacter>()
        )
}