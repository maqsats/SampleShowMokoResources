package com.dna.payments.kmm.data.repository

import com.dna.payments.kmm.domain.model.Character

interface IRemoteData {
    suspend fun getCharactersFromApi(): List<Character>
    suspend fun getCharacterFromApi(id: Int): Character
}