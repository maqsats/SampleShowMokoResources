package daniel.avila.rnm.kmm.data.model.exchange_rate

import kotlinx.serialization.Serializable

@Serializable
data class LocationApiModel(
    val address: String,
    val distance: Double,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val phone: String,
    val tags: List<String>? = emptyList()
)