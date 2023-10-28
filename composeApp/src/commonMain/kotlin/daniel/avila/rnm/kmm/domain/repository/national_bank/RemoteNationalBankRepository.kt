package daniel.avila.rnm.kmm.domain.repository.national_bank

import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBank

interface RemoteNationalBankRepository {
    suspend fun getNationalBankCurrency(): List<NationalBank>
}