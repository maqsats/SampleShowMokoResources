package com.dna.payments.kmm.domain.interactors.use_cases.access_level

import com.dna.payments.kmm.domain.model.permissions.AccessLevel
import com.dna.payments.kmm.domain.model.permissions.Section
import com.dna.payments.kmm.domain.repository.AccessLevelRepository

class AccessLevelUseCase(private val accessLevelRepository: AccessLevelRepository) {
    operator fun invoke(section: Section): List<AccessLevel> {
        return accessLevelRepository.getAccessLevelBySection(section)
    }

    fun hasPermission(section: Section, vararg accessLevels: AccessLevel): Boolean {
        val sectionAccessLevels = accessLevelRepository.getAccessLevelBySection(section)
        return accessLevels.any { sectionAccessLevels.contains(it) }
    }
}