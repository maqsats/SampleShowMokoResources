package daniel.avila.rnm.kmm.data.model.currency

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyApiModel(
    val code: String,
    val currency_logo: String,
    val name: String
)