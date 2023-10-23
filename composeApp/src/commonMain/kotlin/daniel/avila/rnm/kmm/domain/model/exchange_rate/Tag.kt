package daniel.avila.rnm.kmm.domain.model.exchange_rate

enum class Tag {
    CLOSEST,
    BEST_SELL,
    BEST_BUY,
    UNKNOWN;

    companion object {
        fun fromValue(value: String): Tag {
            return when (value) {
                "closest" -> CLOSEST
                "best_sell" -> BEST_SELL
                "best_buy" -> BEST_BUY
                else -> UNKNOWN
            }
        }
    }
}