package daniel.avila.rnm.kmm.data.model.mapper

import daniel.avila.rnm.kmm.data.model.national_bank.NationalBankCurrencyRateApiModel
import daniel.avila.rnm.kmm.domain.model.map.Mapper
import daniel.avila.rnm.kmm.domain.model.national_bank.CurrencyChange
import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency

class NationalBankMapper(private val endPoint: String) :
    Mapper<NationalBankCurrencyRateApiModel, NationalBankCurrency>() {

    override fun map(model: NationalBankCurrencyRateApiModel): NationalBankCurrency =
        NationalBankCurrency(
            currencyCode = model.currency_code,
            currencyId = model.currency_id,
            currencyName = model.currency_name,
            quantity = model.quantity,
            date = model.date.orEmpty(),
            rate = model.rate,
            currencyLogo = endPoint + model.currency_logo.orEmpty(),
            updatedAt = model.updated_at.orEmpty(),
            change = CurrencyChange.fromValue(model.change)
        )
}