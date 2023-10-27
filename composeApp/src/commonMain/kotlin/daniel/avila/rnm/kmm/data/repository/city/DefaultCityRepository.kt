package daniel.avila.rnm.kmm.data.repository.city

import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.domain.params.CityParameters
import daniel.avila.rnm.kmm.domain.repository.city.CityRepository
import daniel.avila.rnm.kmm.domain.repository.city.RemoteCityRepository

class DefaultCityRepository(
    private val remoteCityRepository: RemoteCityRepository
) : CityRepository {

    override suspend fun getCities(param: CityParameters): List<City> =
        remoteCityRepository.getCities(param)
}