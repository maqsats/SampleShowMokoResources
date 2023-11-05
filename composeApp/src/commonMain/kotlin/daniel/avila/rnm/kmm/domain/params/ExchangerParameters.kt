package daniel.avila.rnm.kmm.domain.params

data class ExchangerParameters(
    val cityId: Int,
    val lat: Double,
    val lng: Double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ExchangerParameters

        if (cityId != other.cityId) return false
        if (lat != other.lat) return false
        if (lng != other.lng) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cityId
        result = 31 * result + lat.hashCode()
        result = 31 * result + lng.hashCode()
        return result
    }
}