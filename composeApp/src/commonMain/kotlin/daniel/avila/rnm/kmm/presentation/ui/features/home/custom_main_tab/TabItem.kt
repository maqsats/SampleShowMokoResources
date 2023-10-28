package daniel.avila.rnm.kmm.presentation.ui.features.home.custom_main_tab

import daniel.avila.rnm.kmm.domain.model.exchange_rate.BuyOrSell

data class TabItem(
    val stringResId: String,
    var isSelected: Boolean,
    var buyOrSell: BuyOrSell = BuyOrSell.BUY
)