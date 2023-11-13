package com.dna.payments.kmm.data.preferences;

import com.dna.payments.kmm.domain.model.permissions.AccessLevel
import com.dna.payments.kmm.domain.model.permissions.Section

interface Preferences {
    fun setAuthToken(value: String)

    fun getAuthToken(): String

    fun setRefreshToken(value: String)

    fun getRefreshToken(): String

    fun clear()

    fun setUpdatePayByLinkList(value: Boolean)

    fun isUpdatePayByLinkList(): Boolean

    fun setMerchantName(value: String)

    fun getMerchantName(): String

    fun setSectionAccessLevel(sectionLevelMap: MutableMap<Section, MutableList<AccessLevel>>)

    fun getSectionAccessLevel(): MutableMap<Section, MutableList<AccessLevel>>
}