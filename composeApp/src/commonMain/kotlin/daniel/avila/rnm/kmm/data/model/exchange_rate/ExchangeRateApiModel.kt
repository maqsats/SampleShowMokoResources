package daniel.avila.rnm.kmm.data.model.exchange_rate

data class ExchangeRateApiModel(
    val category_id: Int,
    val currency_rate: CurrencyRate,
    val id: Int,
    val location: Location,
    val logo: Any,
    val name: String,
    val open_hours: List<OpenHour>
)