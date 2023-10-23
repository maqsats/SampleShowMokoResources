package daniel.avila.rnm.kmm.data.model.exchange_rate

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRateApiModel(
    val buy: Double,
    val currency_code: String,
    val quantity: Int,
    val sell: Double,
    val updated_at: String
)