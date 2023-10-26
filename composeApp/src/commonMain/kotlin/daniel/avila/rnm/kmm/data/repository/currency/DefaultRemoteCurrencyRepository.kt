package daniel.avila.rnm.kmm.data.repository.currency

import daniel.avila.rnm.kmm.data.model.currency.CurrenciesListApiModel
import daniel.avila.rnm.kmm.data.model.mapper.CurrencyMapper
import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.domain.repository.currency.RemoteCurrencyRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DefaultRemoteCurrencyRepository(
    private val currencyMapper: CurrencyMapper,
    private val endPoint: String,
    private val httpClient: HttpClient
) :
    RemoteCurrencyRepository {

    override suspend fun getCurrencies(
    ): List<Currency> =
        currencyMapper.map(
            httpClient.get("$endPoint/currencies")
                .body<CurrenciesListApiModel>().currencies
        )
}