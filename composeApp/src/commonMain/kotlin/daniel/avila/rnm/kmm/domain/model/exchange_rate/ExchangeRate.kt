package daniel.avila.rnm.kmm.domain.model.exchange_rate

data class ExchangeRate(
    val categoryId: Int,
    val currencyRate: CurrencyRate,
    val currencyRateList: List<CurrencyRate>,
    val id: Int,
    val location: Location,
    val locationCount: Int,
    val logo: String,
    val name: String,
    val openHours: List<OpenHour>
)