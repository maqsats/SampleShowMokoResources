package daniel.avila.rnm.kmm.data.model.mapper

import daniel.avila.rnm.kmm.data.model.currency.CurrencyApiModel
import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.domain.model.map.Mapper

class CurrencyMapper(private val endPoint: String) : Mapper<CurrencyApiModel, Currency>() {
    override fun map(model: CurrencyApiModel): Currency {
        return Currency(
            code = model.code,
            currencyLogo = endPoint + model.currency_logo,
            name = model.name
        )
    }
}