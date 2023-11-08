package com.dna.payments.kmm.domain.interactors

import com.dna.payments.kmm.domain.IRepository
import com.dna.payments.kmm.domain.interactors.type.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class SwitchCharacterFavoriteUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Int, Boolean>(dispatcher) {
    override suspend fun block(param: Int): Boolean {
        if (repository.isCharacterFavorite(param)) {
            repository.removeCharacterFromFavorite(param)
        } else {
            repository.addCharacterToFavorites(repository.getCharacter(param))
        }
        return repository.isCharacterFavorite(param)
    }
}