package daniel.avila.rnm.kmm.domain.model.city

import kotlinx.serialization.Serializable

@Serializable
data class City(
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val name: String
)