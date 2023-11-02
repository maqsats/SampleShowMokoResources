package daniel.avila.rnm.kmm.domain.interactors.exchanger

import daniel.avila.rnm.kmm.domain.interactors.type.BaseUseCase
import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.domain.params.ExchangerParameters
import daniel.avila.rnm.kmm.domain.repository.exchange_rate.ExchangeRateRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetExchangerUseCase(
    private val repository: ExchangeRateRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<ExchangerParameters, List<ExchangeRate>>(dispatcher) {

    override suspend fun block(param: ExchangerParameters): List<ExchangeRate> =
        repository.getExchangeRateList(param)
}
