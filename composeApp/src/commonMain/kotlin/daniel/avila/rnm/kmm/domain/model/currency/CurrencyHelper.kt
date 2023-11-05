package daniel.avila.rnm.kmm.domain.model.currency

object CurrencyHelper {
    fun getDefaultCurrencyPair() = Pair(
        Currency(
            name = "Kazakhstani Tenge",
            code = "KZT",
            currencyLogo = ""
        ), Currency(
            name = "United States Dollar",
            code = "USD",
            currencyLogo = ""
        )
    )
}