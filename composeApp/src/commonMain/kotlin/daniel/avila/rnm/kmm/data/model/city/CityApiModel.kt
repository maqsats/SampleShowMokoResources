package daniel.avila.rnm.kmm.data.model.city

import kotlinx.serialization.Serializable

@Serializable
data class CityApiModel(
    val id: Int,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val name: String
)