package daniel.avila.rnm.kmm.domain.repository.national_bank

import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency

interface NationalBankRepository {
    suspend fun getNationalBankCurrencyList(): List<NationalBankCurrency>
}