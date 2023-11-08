package daniel.avila.rnm.kmm.domain.model.city

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class City(
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    var isUserLocationUsing: Boolean = false
) : Parcelable