package daniel.avila.rnm.kmm.data.repository.national_bank

import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency
import daniel.avila.rnm.kmm.domain.model.time_period_tab.TimePeriod
import daniel.avila.rnm.kmm.domain.params.CurrencyCode
import daniel.avila.rnm.kmm.domain.repository.national_bank.NationalBankRepository
import daniel.avila.rnm.kmm.domain.repository.national_bank.RemoteNationalBankRepository

class DefaultNationalBankRepository(
    private val remoteNationalBankRepository: RemoteNationalBankRepository
) : NationalBankRepository {

    override suspend fun getNationalBankCurrencyList(): List<NationalBankCurrency> {
        return remoteNationalBankRepository.getNationalBankCurrency()
    }

    override suspend fun getNationalBankCurrencyList(param: Pair<TimePeriod, CurrencyCode>): List<NationalBankCurrency> {
        return remoteNationalBankRepository.getNationalBankCurrency(param)
    }
}