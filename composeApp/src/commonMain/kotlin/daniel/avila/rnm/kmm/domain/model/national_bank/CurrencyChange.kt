package daniel.avila.rnm.kmm.domain.model.national_bank

enum class CurrencyChange(val value: Int) {
    UP(1),
    DOWN(-1);

    companion object {
        fun fromValue(value: Int): CurrencyChange = when (value) {
            1 -> UP
            else -> DOWN
        }
    }
}