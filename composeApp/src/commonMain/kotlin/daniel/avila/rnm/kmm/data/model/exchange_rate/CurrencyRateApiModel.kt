package daniel.avila.rnm.kmm.data.model.exchange_rate

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRateApiModel(
    val buy: Double,
    val currency_code: String,
    val currency_logo: String? = null,
    val quantity: Int,
    val sell: Double,
    val updated_at: String
)