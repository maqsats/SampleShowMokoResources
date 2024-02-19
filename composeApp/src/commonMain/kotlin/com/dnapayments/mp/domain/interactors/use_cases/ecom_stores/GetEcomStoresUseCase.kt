package com.dnapayments.mp.domain.interactors.use_cases.ecom_stores

import com.dnapayments.mp.data.model.stores.StoresItem
import com.dnapayments.mp.domain.model.map.Mapper
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.StoresRepository

class GetEcomStoresUseCase(
    private val repository: StoresRepository
) : Mapper<List<StoresItem>, List<StoresItem>>() {

    suspend operator fun invoke(): Response<List<StoresItem>> = map(repository.getStores())

    override fun mapData(from: List<StoresItem>): List<StoresItem> {
        return from.filter { it.type == "ecom" }
    }
}