package com.dnapayments.mp.domain.interactors.use_cases.currency

import com.dnapayments.mp.data.model.stores.StoresItem
import com.dnapayments.mp.domain.model.currency.Currency
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.StoresRepository

class DefaultCurrencyUseCase(private val storesRepository: StoresRepository) :
    CurrencyUseCase {

    override suspend operator fun invoke(): Response<List<Currency>> =
        when (val response = storesRepository.getStores()) {
            is Response.Error -> Response.Error(response.error)
            is Response.NetworkError -> Response.NetworkError
            is Response.Success -> Response.Success(getCurrencyFromSupportedList(response.data))
            is Response.TokenExpire -> Response.TokenExpire
        }

    override fun getCurrencyFromSupportedList(stores: List<StoresItem>): List<Currency> {
        return getCurrencyListFromStoreItem(stores.first())
    }

    override fun getCurrencyListFromStoreItem(storeItem: StoresItem): List<Currency> {
        val currencyCounts = mutableMapOf<String, Int>()

        storeItem.terminals.forEach { terminal ->
            terminal.supportedCurrencies.forEach { currency ->
                currencyCounts[currency] = currencyCounts.getOrElse(currency) { 0 } + 1
            }
        }

        val allTerminalsCount = storeItem.terminals.size
        val supportedCurrencies =
            currencyCounts.filter { it.value == allTerminalsCount }.keys.toList()

        return supportedCurrencies.map { Currency(it) }
    }
}