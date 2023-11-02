package daniel.avila.rnm.kmm.domain.model.exchanger

import daniel.avila.rnm.kmm.domain.model.exchange_rate.CurrencyRate
import daniel.avila.rnm.kmm.domain.model.exchange_rate.Location

data class Exchanger(
    val categoryId: Int,
    val currencyRates: List<CurrencyRate>,
    val id: Int,
    val locations: List<Location>,
    val logo: String,
    val name: String
)