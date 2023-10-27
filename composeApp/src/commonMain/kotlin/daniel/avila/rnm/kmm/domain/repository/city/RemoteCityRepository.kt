package daniel.avila.rnm.kmm.domain.repository.city

import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.domain.params.CityParameters

interface RemoteCityRepository {
    suspend fun getCities(param: CityParameters): List<City>
}