package com.dnapayments.mp.domain.interactors.use_cases.currency

object CurrencyHelper {
    operator fun invoke(currencyCode: String): String =
        when (currencyCode) {
            "USD" -> "$"
            "EUR" -> "€"
            "GBP" -> "£"
            else -> currencyCode
        }
}