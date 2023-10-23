package daniel.avila.rnm.kmm.data.repository

import daniel.avila.rnm.kmm.data.model.exchange_rate.ExchangeRateApiModel
import daniel.avila.rnm.kmm.data.model.mapper.ExchangeRateMapper
import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.domain.params.ExchangeRateParameters
import daniel.avila.rnm.kmm.domain.repository.RemoteExchangeRateRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DefaultRemoteExchangeRateRepository(
    private val exchangeRateMapper: ExchangeRateMapper,
    private val endPoint: String,
    private val httpClient: HttpClient
) :
    RemoteExchangeRateRepository {

    override suspend fun getExchangeRateList(
        param: ExchangeRateParameters
    ): List<ExchangeRate> =
        exchangeRateMapper.map(
            httpClient.get("$endPoint/exchangers/rate/${param.cityId}/currency/${param.currencyCode}/${param.buyOrSell}/${param.lat}/${param.lng}")
                .body<List<ExchangeRateApiModel>>()
        )
}