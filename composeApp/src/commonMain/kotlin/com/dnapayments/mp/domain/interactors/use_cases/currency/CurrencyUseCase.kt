package com.dnapayments.mp.domain.interactors.use_cases.currency

import com.dnapayments.mp.data.model.stores.StoresItem
import com.dnapayments.mp.domain.model.currency.Currency
import com.dnapayments.mp.domain.network.Response

interface CurrencyUseCase {

    suspend operator fun invoke(): Response<List<Currency>>

    fun getCurrencyFromSupportedList(stores: List<StoresItem>): List<Currency>

    fun getCurrencyListFromStoreItem(storeItem: StoresItem): List<Currency>
}