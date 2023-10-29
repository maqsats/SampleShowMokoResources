package daniel.avila.rnm.kmm.data.model.mapper

import daniel.avila.rnm.kmm.data.model.national_bank.NationalBankCurrencyApiModel
import daniel.avila.rnm.kmm.domain.model.map.Mapper
import daniel.avila.rnm.kmm.domain.model.national_bank.CurrencyChange
import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency

class NationalBankMapper : Mapper<NationalBankCurrencyApiModel, NationalBankCurrency>() {

    override fun map(model: NationalBankCurrencyApiModel): NationalBankCurrency =
        NationalBankCurrency(
            currencyCode = model.currency_code,
            currencyId = model.currency_id,
            currencyName = model.currency_name,
            quantity = model.quantity,
            rate = model.rate,
            updatedAt = model.updated_at,
            change = CurrencyChange.fromValue(model.change)
        )
}