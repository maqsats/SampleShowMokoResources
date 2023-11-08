package daniel.avila.rnm.kmm.domain.interactors.currency

import daniel.avila.rnm.kmm.domain.interactors.type.BaseUseCase
import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.domain.repository.currency.CurrencyRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetCurrencyUseCase(
    private val repository: CurrencyRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, List<Currency>>(dispatcher) {

    override suspend fun block(param: Unit): List<Currency> {
        if (repository.getCachedCurrencies().isNotEmpty())
            return repository.getCachedCurrencies()

        return repository.getCurrencies().also {
            repository.saveCurrenciesToCache(it)
        }
    }
}
