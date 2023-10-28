package daniel.avila.rnm.kmm.data.model.mapper

import daniel.avila.rnm.kmm.data.model.national_bank.NationalBankApiModel
import daniel.avila.rnm.kmm.domain.model.map.Mapper
import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBank

class NationalBankMapper : Mapper<NationalBankApiModel, NationalBank>() {

    override fun map(model: NationalBankApiModel): NationalBank =
        NationalBank(
            currencyCode = model.currency_code,
            currencyId = model.currency_id,
            currencyName = model.currency_name,
            quantity = model.quantity,
            rate = model.rate,
            updatedAt = model.updated_at
        )
}