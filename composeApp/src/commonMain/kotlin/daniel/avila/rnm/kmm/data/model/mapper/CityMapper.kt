package daniel.avila.rnm.kmm.data.model.mapper

import daniel.avila.rnm.kmm.data.model.city.CityApiModel
import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.domain.model.map.Mapper

class CityMapper : Mapper<CityApiModel, City>() {
    override fun map(model: CityApiModel): City {
        return City(
            id = model.id,
            latitude = model.latitude ?: 0.0,
            longitude = model.longitude ?: 0.0,
            name = model.name
        )
    }
}