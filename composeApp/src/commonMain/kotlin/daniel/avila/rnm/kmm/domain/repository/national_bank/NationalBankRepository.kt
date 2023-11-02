package daniel.avila.rnm.kmm.domain.repository.national_bank

import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency
import daniel.avila.rnm.kmm.domain.model.time_period_tab.TimePeriod
import daniel.avila.rnm.kmm.domain.params.CurrencyCode

interface NationalBankRepository {
    suspend fun getNationalBankCurrencyList(): List<NationalBankCurrency>
    suspend fun getNationalBankCurrencyList(param: Pair<TimePeriod, CurrencyCode>): List<NationalBankCurrency>
}