package com.dna.payments.kmm.domain.interactors

import com.dna.payments.kmm.domain.IRepository
import com.dna.payments.kmm.domain.interactors.type.BaseUseCase
import com.dna.payments.kmm.domain.model.Character
import kotlinx.coroutines.CoroutineDispatcher

class GetCharacterUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Int, Character>(dispatcher){
    override suspend fun block(param: Int): Character = repository.getCharacter(param)
}