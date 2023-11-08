package com.dna.payments.kmm.domain.interactors

import com.dna.payments.kmm.domain.IRepository
import com.dna.payments.kmm.domain.interactors.type.BaseUseCase
import com.dna.payments.kmm.domain.model.Character
import kotlinx.coroutines.CoroutineDispatcher

class GetCharactersUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, List<Character>>(dispatcher){
    override suspend fun block(param: Unit): List<Character> = repository.getCharacters()
}