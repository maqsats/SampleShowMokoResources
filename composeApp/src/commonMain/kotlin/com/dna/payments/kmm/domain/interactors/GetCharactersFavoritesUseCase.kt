package com.dna.payments.kmm.domain.interactors

import com.dna.payments.kmm.domain.IRepository
import com.dna.payments.kmm.domain.interactors.type.BaseUseCaseFlow
import com.dna.payments.kmm.domain.model.Character
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetCharactersFavoritesUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCaseFlow<Unit,List<Character>>(dispatcher) {
    override suspend fun build(param: Unit): Flow<List<Character>> = repository.getCharactersFavorites()
}