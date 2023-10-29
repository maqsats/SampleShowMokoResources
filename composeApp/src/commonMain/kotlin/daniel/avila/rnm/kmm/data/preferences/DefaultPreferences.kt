package daniel.avila.rnm.kmm.data.preferences

import com.russhwolf.settings.Settings
import daniel.avila.rnm.kmm.domain.model.city.City
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

class DefaultPreferences : Preferences {
    private val settings = Settings()

    override fun setCity(city: City) {
        settings.putString(CITY_KEY, Json.encodeToString(city))
    }

    override fun getCity(): City? {
        return settings.getStringOrNull(CITY_KEY)?.let { decodeFromString(it) }
    }

    companion object {
        private const val CITY_KEY = "city"
    }
}