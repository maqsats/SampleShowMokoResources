package daniel.avila.rnm.kmm.domain.interactors

import daniel.avila.rnm.kmm.domain.interactors.type.BaseUseCase
import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.domain.params.ExchangeRateParameters
import daniel.avila.rnm.kmm.domain.repository.ExchangeRateRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetExchangeRateUseCase(
    private val repository: ExchangeRateRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<ExchangeRateParameters, List<ExchangeRate>>(dispatcher) {
    override suspend fun block(param: ExchangeRateParameters): List<ExchangeRate> =
        repository.getExchangeRateList(param)
}
