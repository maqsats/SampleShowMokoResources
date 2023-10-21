package daniel.avila.rnm.kmm.domain.model.exchange_rate

data class OpenHour(
    val closeTime: String,
    val dayOfWeek: Int,
    val openTime: String
)