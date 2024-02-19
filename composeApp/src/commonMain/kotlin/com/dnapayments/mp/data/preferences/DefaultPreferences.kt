package com.dnapayments.mp.data.preferences

import com.dnapayments.mp.domain.model.permissions.AccessLevel
import com.dnapayments.mp.domain.model.permissions.Section
import com.russhwolf.settings.Settings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DefaultPreferences : Preferences {
    private val settings = Settings()


    companion object {
        private const val AUTH_TOKEN = "AUTH_TOKEN"
        private const val PIN_CODE = "PIN_CODE"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val UPDATE_PAY_BY_LINK_LIST = "UPDATE_PAY_BY_LINK_LIST"
        private const val MERCHANT_NAME = "MERCHANT_NAME"
        private const val SECTION_ACCESS_LEVEL = "SECTION_ACCESS_LEVEL"
    }

    override fun setAuthToken(value: String) {
        settings.putString(AUTH_TOKEN, value)
    }

    override fun getAuthToken(): String {
        return settings.getString(AUTH_TOKEN, "")
    }

    override fun setRefreshToken(value: String) {
        settings.putString(REFRESH_TOKEN, value)
    }

    override fun getRefreshToken(): String {
        return settings.getString(REFRESH_TOKEN, "")
    }

    override fun clear() {
        val sectionLevel = getSectionAccessLevel()
        val refreshToken = getRefreshToken()
        settings.clear()
        setRefreshToken(refreshToken)
        setSectionAccessLevel(sectionLevel)
    }

    override fun setUpdatePayByLinkList(value: Boolean) {
        settings.putBoolean(UPDATE_PAY_BY_LINK_LIST, value)
    }

    override fun isUpdatePayByLinkList(): Boolean {
        return settings.getBoolean(UPDATE_PAY_BY_LINK_LIST, false)
    }

    override fun setMerchantName(value: String) {
        settings.putString(MERCHANT_NAME, value)
    }

    override fun getMerchantName(): String {
        return settings.getString(MERCHANT_NAME, "")
    }

    override fun setSectionAccessLevel(sectionLevelMap: MutableMap<Section, MutableList<AccessLevel>>) {
        val jsonString = Json.encodeToString(sectionLevelMap)
        settings.putString(SECTION_ACCESS_LEVEL, jsonString)
    }

    override fun getSectionAccessLevel(): MutableMap<Section, MutableList<AccessLevel>> {

        val json = settings.getString(SECTION_ACCESS_LEVEL, "")

        // If no data is found, return a map with all sections set to AccessLevel.READ
        if (json.isEmpty()) {
            val sectionAccessLevels = mutableMapOf<Section, MutableList<AccessLevel>>()
            for (section in Section.values()) {
                sectionAccessLevels[section] = mutableListOf(AccessLevel.READ)
            }
            return sectionAccessLevels
        }

        return Json.decodeFromString(json) ?: mutableMapOf()
    }

    override fun getPinCode(): String =
        settings.getString(PIN_CODE, "")

    override fun setPinCode(pinCode: String) {
        settings.putString(PIN_CODE, pinCode)
    }
}