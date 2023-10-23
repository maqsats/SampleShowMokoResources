package daniel.avila.rnm.kmm.domain.model.exchange_rate

data class ExchangeRate(
    val categoryId: Int,
    val currencyRate: CurrencyRate,
    val id: Int,
    val location: Location,
    val logo: String,
    val name: String,
    val openHours: List<OpenHour>
)