package daniel.avila.rnm.kmm.domain.interactors.city

import daniel.avila.rnm.kmm.domain.interactors.type.BaseUseCase
import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.domain.params.CityParameters
import daniel.avila.rnm.kmm.domain.repository.city.CityRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetCityUseCase(
    private val repository: CityRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<CityParameters, List<City>>(dispatcher) {

    override suspend fun block(param: CityParameters): List<City> =
        repository.getCities(param)
}
