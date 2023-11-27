package com.dna.payments.kmm.data.repository

import com.dna.payments.kmm.data.preferences.Preferences
import com.dna.payments.kmm.domain.model.permissions.AccessLevel
import com.dna.payments.kmm.domain.model.permissions.Section
import com.dna.payments.kmm.domain.repository.AccessLevelRepository

class DefaultAccessLevelRepository(private val preference: Preferences) : AccessLevelRepository {
    override fun getAccessLevelBySection(section: Section): List<AccessLevel> {
        return preference.getSectionAccessLevel()[section] ?: mutableListOf(AccessLevel.NO_ACCESS)
    }
}