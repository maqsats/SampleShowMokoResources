package daniel.avila.rnm.kmm.domain.model.national_bank

data class NationalBank(
    val currencyCode: String,
    val currencyId: Int,
    val currencyName: String,
    val quantity: Int,
    val rate: Double,
    val updatedAt: String
)