package daniel.avila.rnm.kmm.data.repository.currency

import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.domain.repository.currency.CacheCurrencyRepository
import daniel.avila.rnm.kmm.domain.repository.currency.CurrencyRepository

class DefaultCurrencyRepository(
    private val remoteCurrencyRepository: DefaultRemoteCurrencyRepository,
    private val cacheCurrencyRepository: CacheCurrencyRepository
) : CurrencyRepository {

    override suspend fun getCurrencies(): List<Currency> =
        remoteCurrencyRepository.getCurrencies()

    override suspend fun saveCurrenciesToCache(currencies: List<Currency>) {
        cacheCurrencyRepository.saveCurrenciesToCache(currencies)
    }

    override suspend fun getCachedCurrencies(): List<Currency> =
        cacheCurrencyRepository.getCachedCurrencies()
}