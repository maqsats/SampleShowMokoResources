package daniel.avila.rnm.kmm.domain.model.exchanger

data class ExchangerTab(
    val stringResId: String,
    var isSelected: Boolean,
    var exchangerType: ExchangerType = ExchangerType.ALL
)