package daniel.avila.rnm.kmm.data.model.national_bank

import kotlinx.serialization.Serializable

@Serializable
data class NationalBankCurrencyApiModel(
    val change: Int,
    val currency_code: String,
    val currency_id: Int,
    val currency_name: String,
    val quantity: Int,
    val rate: Double,
    val updated_at: String
)