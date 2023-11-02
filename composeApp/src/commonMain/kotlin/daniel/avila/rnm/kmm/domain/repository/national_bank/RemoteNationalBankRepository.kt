package daniel.avila.rnm.kmm.domain.repository.national_bank

import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency
import daniel.avila.rnm.kmm.domain.model.time_period_tab.TimePeriod
import daniel.avila.rnm.kmm.domain.params.CurrencyCode

interface RemoteNationalBankRepository {
    suspend fun getNationalBankCurrency(): List<NationalBankCurrency>
    suspend fun getNationalBankCurrency(param: Pair<TimePeriod, CurrencyCode>): List<NationalBankCurrency>
}