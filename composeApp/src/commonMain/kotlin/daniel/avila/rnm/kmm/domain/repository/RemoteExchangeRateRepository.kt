package daniel.avila.rnm.kmm.domain.repository

import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.domain.params.ExchangeRateParameters

interface RemoteExchangeRateRepository {
    suspend fun getExchangeRateList(
        param: ExchangeRateParameters
    ): List<ExchangeRate>
}