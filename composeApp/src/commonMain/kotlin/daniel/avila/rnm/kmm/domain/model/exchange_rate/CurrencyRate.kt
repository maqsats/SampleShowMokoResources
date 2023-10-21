package daniel.avila.rnm.kmm.domain.model.exchange_rate

data class CurrencyRate(
    val buy: Double,
    val currency_code: String,
    val quantity: Int,
    val sell: Double,
    val updated_at: String
)