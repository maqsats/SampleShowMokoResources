package daniel.avila.rnm.kmm.data.preferences;

import daniel.avila.rnm.kmm.domain.model.city.City

interface Preferences {

    fun setCity(city: City)

    fun getCity(): City?
}