package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.data.model.profile.Profile
import com.dna.payments.kmm.domain.network.Response

interface ProfileRepository {

    suspend fun getProfile(): Response<Profile>
}