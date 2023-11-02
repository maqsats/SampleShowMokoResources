package daniel.avila.rnm.kmm.domain.interactors.national_bank

import daniel.avila.rnm.kmm.domain.interactors.type.BaseUseCase
import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency
import daniel.avila.rnm.kmm.domain.model.time_period_tab.TimePeriod
import daniel.avila.rnm.kmm.domain.params.CurrencyCode
import daniel.avila.rnm.kmm.domain.repository.national_bank.NationalBankRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetNBCurrencyByRangeUseCase(
    private val repository: NationalBankRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Pair<TimePeriod, CurrencyCode>, List<NationalBankCurrency>>(dispatcher) {

    override suspend fun block(param: Pair<TimePeriod, CurrencyCode>): List<NationalBankCurrency> =
        repository.getNationalBankCurrencyList(param)
}
