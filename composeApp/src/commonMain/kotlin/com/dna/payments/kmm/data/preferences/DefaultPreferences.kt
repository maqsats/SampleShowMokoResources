package com.dna.payments.kmm.data.preferences

import com.russhwolf.settings.Settings

class DefaultPreferences : Preferences {
    private val settings = Settings()


    companion object {
        private const val CITY_KEY = "city"
    }
}