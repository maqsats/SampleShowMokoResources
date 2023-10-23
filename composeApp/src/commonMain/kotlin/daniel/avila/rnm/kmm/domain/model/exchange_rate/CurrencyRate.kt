package daniel.avila.rnm.kmm.domain.model.exchange_rate

data class CurrencyRate(
    val buy: Double,
    val currencyCode: String,
    val quantity: Int,
    val sell: Double,
    val updatedAt: String
)