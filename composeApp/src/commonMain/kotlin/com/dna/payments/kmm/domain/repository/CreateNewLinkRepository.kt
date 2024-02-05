package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.data.model.create_new_link.CreateNewLinkRequest
import com.dna.payments.kmm.domain.model.create_new_link.CreateNewLinkData
import com.dna.payments.kmm.domain.network.Response

interface CreateNewLinkRepository {

    suspend fun createNewLink(
        createNewLinkRequest: CreateNewLinkRequest
    ): Response<CreateNewLinkData>
}