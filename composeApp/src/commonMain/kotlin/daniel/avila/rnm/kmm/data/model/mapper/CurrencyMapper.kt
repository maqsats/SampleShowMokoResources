package daniel.avila.rnm.kmm.data.model.mapper

import daniel.avila.rnm.kmm.data.model.currency.CurrencyApiModel
import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.domain.model.map.Mapper
import daniel.avila.rnm.kmm.utils.extension.defaultIfNull

class CurrencyMapper(private val endPoint: String) : Mapper<CurrencyApiModel, Currency>() {
    override fun map(model: CurrencyApiModel): Currency {
        return Currency(
            code = model.code,
            currencyLogo = model.currency_logo.defaultIfNull(endPoint),
            name = model.name
        )
    }
}