package daniel.avila.rnm.kmm.data.model.exchange_rate

data class Location(
    val address: String,
    val city_id: Int,
    val distance: Double,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val phone: String,
    val tags: List<String>
)