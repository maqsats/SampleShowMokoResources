package daniel.avila.rnm.kmm.domain.repository.exchange_rate

import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.domain.params.ExchangeRateParameters
import daniel.avila.rnm.kmm.domain.params.ExchangerParameters

interface ExchangeRateRepository {
    suspend fun getExchangeRateList(
        param: ExchangeRateParameters
    ): List<ExchangeRate>

    suspend fun getExchangeRateList(param: ExchangerParameters): List<ExchangeRate>
}