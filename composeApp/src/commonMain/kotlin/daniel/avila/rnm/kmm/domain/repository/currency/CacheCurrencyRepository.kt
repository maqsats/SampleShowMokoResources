package daniel.avila.rnm.kmm.domain.repository.currency

import daniel.avila.rnm.kmm.domain.model.currency.Currency

interface CacheCurrencyRepository {
    suspend fun saveCurrenciesToCache(currencies: List<Currency>)
    suspend fun getCachedCurrencies(): List<Currency>
}