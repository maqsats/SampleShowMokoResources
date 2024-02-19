package com.dnapayments.mp.data.repository

import com.dnapayments.mp.data.preferences.Preferences
import com.dnapayments.mp.domain.model.permissions.AccessLevel
import com.dnapayments.mp.domain.model.permissions.Section
import com.dnapayments.mp.domain.repository.AccessLevelRepository

class DefaultAccessLevelRepository(private val preference: Preferences) : AccessLevelRepository {
    override fun getAccessLevelBySection(section: Section): List<AccessLevel> {
        return preference.getSectionAccessLevel()[section] ?: mutableListOf(AccessLevel.NO_ACCESS)
    }
}