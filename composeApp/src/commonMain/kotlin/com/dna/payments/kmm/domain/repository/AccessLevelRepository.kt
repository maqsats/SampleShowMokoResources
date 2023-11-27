package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.domain.model.permissions.AccessLevel
import com.dna.payments.kmm.domain.model.permissions.Section

interface AccessLevelRepository {
    fun getAccessLevelBySection(section: Section): List<AccessLevel>
}