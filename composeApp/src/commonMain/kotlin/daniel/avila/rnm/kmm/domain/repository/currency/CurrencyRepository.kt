package daniel.avila.rnm.kmm.domain.repository.currency

import daniel.avila.rnm.kmm.domain.model.currency.Currency

interface CurrencyRepository {
    suspend fun getCurrencies(): List<Currency>
}