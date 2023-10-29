package daniel.avila.rnm.kmm.domain.model.exchange_rate

data class Location(
    val address: String,
    val distance: Double,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val phone: String,
    val tags: List<Tag>
)