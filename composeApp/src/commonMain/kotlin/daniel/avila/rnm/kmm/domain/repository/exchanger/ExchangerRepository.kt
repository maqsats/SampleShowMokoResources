package daniel.avila.rnm.kmm.domain.repository.exchanger

import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.domain.params.ExchangeRateParameters

interface ExchangerRepository {
    suspend fun getExchangerList(
        param: ExchangeRateParameters
    ): List<ExchangeRate>
}