package com.dna.payments.kmm.domain.interactors.use_cases.ecom_stores

import com.dna.payments.kmm.data.model.stores.StoresItem
import com.dna.payments.kmm.domain.model.map.Mapper
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.StoresRepository

class GetEcomStoresUseCase(
    private val repository: StoresRepository
) : Mapper<List<StoresItem>, List<StoresItem>>() {

    suspend operator fun invoke(): Response<List<StoresItem>> = map(repository.getStores())

    override fun mapData(from: List<StoresItem>): List<StoresItem> {
        return from.filter { it.type == "ecom" }
    }
}