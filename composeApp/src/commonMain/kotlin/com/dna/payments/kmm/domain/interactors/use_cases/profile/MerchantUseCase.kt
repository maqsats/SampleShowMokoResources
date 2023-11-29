package com.dna.payments.kmm.domain.interactors.use_cases.profile

import com.dna.payments.kmm.data.model.profile.Merchant
import com.dna.payments.kmm.data.model.profile.Profile
import com.dna.payments.kmm.data.preferences.Preferences
import com.dna.payments.kmm.domain.model.map.Mapper
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.ProfileRepository

class MerchantUseCase(
    private val profileRepository: ProfileRepository,
    private val preference: Preferences
) : Mapper<Profile, List<Merchant>>() {

    suspend operator fun invoke(): Response<List<Merchant>> =
        map(profileRepository.getProfile())

    override fun mapData(from: Profile): List<Merchant> {
        if (preference.getMerchantName().isEmpty()) {
            return from.merchants.sortedWith(compareByDescending { it.isDefault })
        }
        return from.merchants.sortedBy { it.merchantId != preference.getMerchantName() }
    }
}