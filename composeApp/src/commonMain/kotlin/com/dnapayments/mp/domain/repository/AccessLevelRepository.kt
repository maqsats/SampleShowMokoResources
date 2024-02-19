package com.dnapayments.mp.domain.repository

import com.dnapayments.mp.domain.model.permissions.AccessLevel
import com.dnapayments.mp.domain.model.permissions.Section

interface AccessLevelRepository {
    fun getAccessLevelBySection(section: Section): List<AccessLevel>
}