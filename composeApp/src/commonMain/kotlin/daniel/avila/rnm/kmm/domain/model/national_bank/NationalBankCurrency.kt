package daniel.avila.rnm.kmm.domain.model.national_bank

data class NationalBankCurrency(
    val change: CurrencyChange,
    val currencyCode: String,
    val currencyId: Int,
    val currencyLogo: String,
    val currencyName: String,
    val date: String,
    val quantity: Int,
    val rate: Double,
    val updatedAt: String
)