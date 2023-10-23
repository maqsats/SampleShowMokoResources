package daniel.avila.rnm.kmm.data.repository

import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.domain.params.ExchangeRateParameters
import daniel.avila.rnm.kmm.domain.repository.ExchangeRateRepository
import daniel.avila.rnm.kmm.domain.repository.RemoteExchangeRateRepository

class DefaultExchangeRateRepository(
    private val remoteExchangeRateRepository: RemoteExchangeRateRepository
) : ExchangeRateRepository {

    override suspend fun getExchangeRateList(
        param: ExchangeRateParameters
    ): List<ExchangeRate> {
        return remoteExchangeRateRepository.getExchangeRateList(
            param
        )
    }
}