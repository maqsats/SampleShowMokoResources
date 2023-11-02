package daniel.avila.rnm.kmm.data.model.national_bank

import kotlinx.serialization.Serializable

@Serializable
data class NationalBankCurrencyRateApiModel(
    val change: Int,
    val currency_code: String,
    val currency_logo: String? = null,
    val currency_id: Int,
    val currency_name: String,
    val date: String? = null,
    val quantity: Int,
    val rate: Double,
    val updated_at: String? = null
)