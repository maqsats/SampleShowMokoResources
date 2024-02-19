package com.dnapayments.mp.domain.repository

import com.dnapayments.mp.data.model.stores.StoresItem
import com.dnapayments.mp.domain.network.Response

interface StoresRepository {

    suspend fun getStores(): Response<List<StoresItem>>
}