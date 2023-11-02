package daniel.avila.rnm.kmm.domain.interactors.national_bank

import daniel.avila.rnm.kmm.domain.interactors.type.BaseUseCase
import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency
import daniel.avila.rnm.kmm.domain.repository.national_bank.NationalBankRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetNationalBankCurrencyUseCase(
    private val repository: NationalBankRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, List<NationalBankCurrency>>(dispatcher) {

    override suspend fun block(param: Unit): List<NationalBankCurrency> =
        repository.getNationalBankCurrencyList()
}
