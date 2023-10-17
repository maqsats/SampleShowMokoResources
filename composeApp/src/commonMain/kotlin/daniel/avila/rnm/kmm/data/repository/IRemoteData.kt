package daniel.avila.rnm.kmm.data.repository

import daniel.avila.rnm.kmm.domain.model.Character

interface IRemoteData {
    suspend fun getCharactersFromApi(): List<Character>
    suspend fun getCharacterFromApi(id: Int): Character
}