package daniel.avila.rnm.kmm.data.model.exchange_rate

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRateApiModel(
    val category_id: Int,
    val currency_rate: CurrencyRateApiModel,
    val id: Int,
    val location: LocationApiModel,
    val logo: String? = null,
    val name: String,
    val open_hours: List<OpenHourApiModel>
)