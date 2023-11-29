package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.data.model.stores.StoresItem
import com.dna.payments.kmm.domain.network.Response

interface StoresRepository {

    suspend fun getStores(): Response<List<StoresItem>>
}