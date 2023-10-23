package daniel.avila.rnm.kmm.data.model.exchange_rate

import kotlinx.serialization.Serializable

@Serializable
data class OpenHourApiModel(
    val close_time: String,
    val day_of_week: Int,
    val open_time: String
)