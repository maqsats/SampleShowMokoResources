package daniel.avila.rnm.kmm.domain.model.exchange_rate

data class Location(
    val address: String,
    val cityId: Int,
    val distance: Double,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val phone: String,
    val tags: List<Tag>
)