package daniel.avila.rnm.kmm.data.model.exchange_rate

data class OpenHour(
    val close_time: String,
    val day_of_week: Int,
    val open_time: String
)