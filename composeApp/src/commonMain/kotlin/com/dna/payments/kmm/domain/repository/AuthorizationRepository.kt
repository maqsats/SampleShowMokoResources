package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.data.model.authorization.AuthToken
import com.dna.payments.kmm.domain.network.Response

interface AuthorizationRepository {

    suspend fun getUserToken(
        username: String,
        password: String
    ): Response<AuthToken>

    suspend fun updateToken(
        refreshToken: String
    ): Response<AuthToken>

    suspend fun changeMerchant(merchantId: String): Response<AuthToken>
}