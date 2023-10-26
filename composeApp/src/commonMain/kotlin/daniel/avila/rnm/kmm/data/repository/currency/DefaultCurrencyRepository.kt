package daniel.avila.rnm.kmm.data.repository.currency

import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.domain.repository.currency.CurrencyRepository

class DefaultCurrencyRepository(
    private val remoteCurrencyRepository: DefaultRemoteCurrencyRepository
) : CurrencyRepository {

    override suspend fun getCurrencies(): List<Currency> =
        remoteCurrencyRepository.getCurrencies()
}