package com.dnapayments.mp.domain.repository

import com.dnapayments.mp.data.model.profile.Profile
import com.dnapayments.mp.domain.network.Response

interface ProfileRepository {

    suspend fun getProfile(): Response<Profile>
}