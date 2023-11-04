package daniel.avila.rnm.kmm.presentation.ui.features.calculator.exchange_list_main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.seiko.imageloader.rememberImagePainter
import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.domain.model.exchange_rate.BuyOrSell
import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.presentation.ui.common.RoundedBackground
import daniel.avila.rnm.kmm.utils.extension.formatDistance
import daniel.avila.rnm.kmm.utils.extension.formatMoney

@Composable
fun ExchangeRateItem(
    modifier: Modifier = Modifier,
    item: ExchangeRate,
    isFirst: Boolean = false,
    buyOrSell: BuyOrSell,
    onClick: () -> Unit,
    inputText: String,
    currencies: Pair<Currency, Currency>
) {
    val addBorder = item.location.tags.isNotEmpty()

    Box(modifier = modifier.fillMaxWidth().wrapContentHeight()) {
        Row(
            modifier = Modifier.wrapContentHeight()
                .fillMaxWidth()
                .padding(start = 10.dp, top = if (addBorder) 14.dp else 0.dp, end = 10.dp)
                .border(
                    BorderStroke(
                        1.dp,
                        when {
                            addBorder && isFirst -> MaterialTheme.colors.surface
                            addBorder -> MaterialTheme.colors.secondary
                            else -> Color.Transparent
                        }
                    ),
                    shape = RoundedCornerShape(5.dp)
                ).clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource()
                ) {
                    onClick()
                }
                .padding(start = 5.dp, end = 5.dp, top = 15.dp, bottom = 15.dp)
                .zIndex(0f)
        ) {
            Image(
                painter = rememberImagePainter(item.logo),
                contentDescription = null,
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f).wrapContentHeight(),
                verticalArrangement = Arrangement.Center
            ) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        item.name,
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        when (buyOrSell) {
                            BuyOrSell.BUY -> item.currencyRate.buy.formatMoney(inputText) + " " + currencies.first.code
                            BuyOrSell.SELL -> item.currencyRate.sell.formatMoney(inputText) + " " + currencies.first.code
                        }, style = MaterialTheme.typography.button,
                        modifier = Modifier.wrapContentWidth()
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "~" + item.location.distance.formatDistance() + " • " + item.location.address,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        "1 ${currencies.second.code} = " + when (buyOrSell) {
                            BuyOrSell.BUY -> item.currencyRate.buy.toString()
                            BuyOrSell.SELL -> item.currencyRate.sell.toString()
                        }, style = MaterialTheme.typography.h6,
                        modifier = Modifier.wrapContentWidth()
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 15.dp, end = 15.dp, top = if (addBorder) 5.dp else 0.dp),
        ) {
            item.location.tags.forEach { tagItem ->
                val isFillBlue = tagItem == item.location.tags.first() && isFirst
                RoundedBackground(
                    modifier = Modifier.wrapContentWidth(),
                    backgroundColor = if (isFillBlue) MaterialTheme.colors.surface else MaterialTheme.colors.secondary,
                    border = 10.dp,
                    height = 18.dp,
                    paddingHorizontal = 8.dp,
                ) {
                    Text(
                        text = tagItem.displayName,
                        style = MaterialTheme.typography.h5,
                        color = if (isFillBlue) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary,
                    )
                }
            }
        }
    }
}