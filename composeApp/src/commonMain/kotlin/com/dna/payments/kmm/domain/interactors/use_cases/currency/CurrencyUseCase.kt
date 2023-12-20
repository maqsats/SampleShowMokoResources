package com.dna.payments.kmm.domain.interactors.use_cases.currency

import com.dna.payments.kmm.data.model.stores.StoresItem
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.network.Response

interface CurrencyUseCase {
    suspend fun getCurrencyList(): Response<List<Currency>>

    fun getCurrencyFromSupportedList(stores: List<StoresItem>): List<Currency>

    fun getCurrencyListFromStoreItem(storeItem: StoresItem): List<Currency>
}