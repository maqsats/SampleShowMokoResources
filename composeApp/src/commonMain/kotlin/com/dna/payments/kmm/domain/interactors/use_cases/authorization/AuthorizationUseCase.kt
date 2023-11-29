package com.dna.payments.kmm.domain.interactors.use_cases.authorization

import com.dna.payments.kmm.data.model.authorization.AuthToken
import com.dna.payments.kmm.data.preferences.Preferences
import com.dna.payments.kmm.domain.model.map.Mapper
import com.dna.payments.kmm.domain.model.permissions.AccessLevel
import com.dna.payments.kmm.domain.model.permissions.Section
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.AuthorizationRepository

class AuthorizationUseCase(
    private val repository: AuthorizationRepository,
    private val preference: Preferences
) : Mapper<AuthToken, Unit>() {

    override fun mapData(from: AuthToken) {
        saveData(from)
    }

    suspend operator fun invoke(userName: String, password: String): Response<Unit> =
        map(repository.getUserToken(userName, password))

    suspend fun updateToken(): Response<Unit> =
        map(repository.updateToken(preference.getRefreshToken()))

    suspend fun changeMerchant(merchantId: String): Response<Unit> =
        when (val response = repository.changeMerchant(merchantId)) {
            is Response.Error -> Response.Error(response.error)
            is Response.NetworkError -> Response.NetworkError
            is Response.TokenExpire -> Response.TokenExpire
            is Response.Success -> {
                saveData(response.data, merchantId)
                Response.Success(Unit)
            }
        }

    private fun saveData(data: AuthToken, merchantId: String? = null) {
        preference.setAuthToken(data.accessToken)
        preference.setRefreshToken(data.refreshToken)
        merchantId?.let {
            preference.setMerchantName(it)
        }

        val sectionLevelMap = mutableMapOf<Section, MutableList<AccessLevel>>()

        if (data.permissions.isNullOrEmpty())
            return

        data.permissions.forEach { permission ->
            val (sectionStr, levelStr) = permission.split(".")
            val section = Section.values().find { it.value == sectionStr }
            val level = AccessLevel.values().find { it.value == levelStr }

            if (section != null && level != null) {
                sectionLevelMap.getOrPut(section) { mutableListOf() }.add(level)
            }
        }

        preference.setSectionAccessLevel(sectionLevelMap)
    }
}