package daniel.avila.rnm.kmm.domain.interactors.national_bank

import daniel.avila.rnm.kmm.domain.interactors.type.BaseUseCase
import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBank
import daniel.avila.rnm.kmm.domain.repository.national_bank.NationalBankRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetNationalBankUseCase(
    private val repository: NationalBankRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, List<NationalBank>>(dispatcher) {

    override suspend fun block(param: Unit): List<NationalBank> =
        repository.getNationalBankCurrencyList()
}
