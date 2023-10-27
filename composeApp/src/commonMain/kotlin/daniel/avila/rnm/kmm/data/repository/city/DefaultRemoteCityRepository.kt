package daniel.avila.rnm.kmm.data.repository.city

import daniel.avila.rnm.kmm.data.model.city.CityListApiModel
import daniel.avila.rnm.kmm.data.model.mapper.CityMapper
import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.domain.params.CityParameters
import daniel.avila.rnm.kmm.domain.repository.city.RemoteCityRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DefaultRemoteCityRepository(
    private val cityMapper: CityMapper,
    private val endPoint: String,
    private val httpClient: HttpClient
) :
    RemoteCityRepository {

    override suspend fun getCities(param: CityParameters): List<City> =
        cityMapper.map(
            httpClient.get("$endPoint/country/${param.countryId}/cities")
                .body<CityListApiModel>().cities
        )
}