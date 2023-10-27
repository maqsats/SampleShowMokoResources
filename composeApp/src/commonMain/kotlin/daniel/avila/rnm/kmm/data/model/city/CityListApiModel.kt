package daniel.avila.rnm.kmm.data.model.city

import kotlinx.serialization.Serializable

@Serializable
data class CityListApiModel(
    val cities: List<CityApiModel>,
    val id: Int,
    val name: String
)