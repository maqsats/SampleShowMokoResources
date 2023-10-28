package daniel.avila.rnm.kmm.data.repository.national_bank

import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBank
import daniel.avila.rnm.kmm.domain.repository.national_bank.NationalBankRepository
import daniel.avila.rnm.kmm.domain.repository.national_bank.RemoteNationalBankRepository

class DefaultNationalBankRepository(
    private val remoteNationalBankRepository: RemoteNationalBankRepository
) : NationalBankRepository {

    override suspend fun getNationalBankCurrencyList(): List<NationalBank> {
        return remoteNationalBankRepository.getNationalBankCurrency()
    }
}