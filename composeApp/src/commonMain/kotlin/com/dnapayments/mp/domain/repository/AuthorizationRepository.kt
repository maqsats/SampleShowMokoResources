package com.dnapayments.mp.domain.repository

import com.dnapayments.mp.data.model.authorization.AuthToken
import com.dnapayments.mp.domain.network.Response

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