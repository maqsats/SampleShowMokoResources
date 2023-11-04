package daniel.avila.rnm.kmm.domain.params

data class ExchangeRateParameters(
    val cityId: Int,
    val currencyCode: String,
    val lat: Double,
    val lng: Double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ExchangeRateParameters) return false

        if (cityId != other.cityId) return false
        if (currencyCode != other.currencyCode) return false
        if (lat != other.lat) return false
        if (lng != other.lng) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cityId
        result = 31 * result + currencyCode.hashCode()
        result = 31 * result + lat.hashCode()
        result = 31 * result + lng.hashCode()
        return result
    }
}