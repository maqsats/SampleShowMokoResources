package daniel.avila.rnm.kmm.data.repository.currency

import daniel.avila.rnm.kmm.data.cache.sqldelight.SharedDatabase
import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.domain.repository.currency.CacheCurrencyRepository

class DefaultCacheCurrencyRepository(
    private val sharedDatabase: SharedDatabase,
) :
    CacheCurrencyRepository {

    override suspend fun saveCurrenciesToCache(currencies: List<Currency>) {
        currencies.forEach { currency ->
            sharedDatabase {
                it.appDatabaseQueries.insertCurrency(
                    code = currency.code,
                    currencyLogo = currency.currencyLogo,
                    name = currency.name
                )
            }
        }
    }

    override suspend fun getCachedCurrencies(): List<Currency> {
        return sharedDatabase {
            it.appDatabaseQueries.selectAllCurrency(::mapCurrency).executeAsList()
        }
    }

    private fun mapCurrency(
        code: String,
        name: String,
        currencyLogo: String
    ): Currency = Currency(code, currencyLogo, name)
}