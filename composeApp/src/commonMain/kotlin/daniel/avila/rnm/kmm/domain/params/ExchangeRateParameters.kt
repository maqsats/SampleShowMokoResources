package daniel.avila.rnm.kmm.domain.params

data class ExchangeRateParameters(
    val cityId: Int,
    val currencyCode: String,
    val buyOrSell: String,
    val lat: Double,
    val lng: Double
)