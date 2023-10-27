package daniel.avila.rnm.kmm.data.repository.exchange_rate

import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.domain.params.ExchangeRateParameters
import daniel.avila.rnm.kmm.domain.repository.exchange_rate.ExchangeRateRepository
import daniel.avila.rnm.kmm.domain.repository.exchange_rate.RemoteExchangeRateRepository

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