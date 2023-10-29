package daniel.avila.rnm.kmm.data.repository.national_bank

import daniel.avila.rnm.kmm.data.model.mapper.NationalBankMapper
import daniel.avila.rnm.kmm.data.model.national_bank.NationalBankCurrencyApiModel
import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency
import daniel.avila.rnm.kmm.domain.repository.national_bank.RemoteNationalBankRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DefaultRemoteNationalBankRepository(
    private val nationalBankMapper: NationalBankMapper,
    private val endPoint: String,
    private val httpClient: HttpClient
) :
    RemoteNationalBankRepository {

    override suspend fun getNationalBankCurrency(): List<NationalBankCurrency> =
        nationalBankMapper.map(
            httpClient.get("$endPoint/exchanger/nb/rates/today")
                .body<List<NationalBankCurrencyApiModel>>()
        )
}