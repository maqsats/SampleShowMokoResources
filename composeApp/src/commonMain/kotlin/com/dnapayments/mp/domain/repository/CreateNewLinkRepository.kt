package com.dnapayments.mp.domain.repository

import com.dnapayments.mp.data.model.create_new_link.CreateNewLinkRequest
import com.dnapayments.mp.domain.model.create_new_link.CreateNewLinkData
import com.dnapayments.mp.domain.network.Response

interface CreateNewLinkRepository {

    suspend fun createNewLink(
        createNewLinkRequest: CreateNewLinkRequest
    ): Response<CreateNewLinkData>
}