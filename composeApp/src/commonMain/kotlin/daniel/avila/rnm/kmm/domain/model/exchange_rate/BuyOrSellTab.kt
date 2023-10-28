package daniel.avila.rnm.kmm.domain.model.exchange_rate

data class BuyOrSellTab(
    val stringResId: String,
    var isSelected: Boolean,
    var buyOrSell: BuyOrSell = BuyOrSell.BUY
)