package com.dnapayments.mp.domain.interactors.use_cases.profile

import com.dnapayments.mp.data.model.profile.Merchant
import com.dnapayments.mp.data.model.profile.Profile
import com.dnapayments.mp.data.preferences.Preferences
import com.dnapayments.mp.domain.model.map.Mapper
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.ProfileRepository

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