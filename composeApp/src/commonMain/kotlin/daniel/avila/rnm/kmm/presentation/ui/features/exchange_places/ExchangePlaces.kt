package daniel.avila.rnm.kmm.presentation.ui.features.exchange_places

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.seiko.imageloader.rememberAsyncImagePainter
import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.presentation.ui.common.RoundedBackground

@Composable
fun ExchangePlaces(modifier: Modifier = Modifier, item: ExchangeRate, isFirst: Boolean = false) {

    val addBorder = item.location.tags.isNotEmpty()
    Box(modifier = modifier) {
        Row(
            modifier = Modifier.wrapContentHeight()
                .fillMaxWidth()
                .padding(start = 10.dp, top = if (addBorder) 9.dp else 0.dp, end = 10.dp)
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
                )
                .padding(start = 5.dp, end = 5.dp, top = 15.dp, bottom = 15.dp)
                .zIndex(0f)
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.logo),
                contentDescription = null,
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                Text(item.name, style = MaterialTheme.typography.button)

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    item.location.distance.toInt().toString() + " " + item.location.address,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.h6
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.wrapContentWidth(), horizontalAlignment = Alignment.End) {

                Text(item.currencyRate.buy.toString(), style = MaterialTheme.typography.button)

                Spacer(modifier = Modifier.height(3.dp))

                Text(item.currencyRate.buy.toString(), style = MaterialTheme.typography.h6)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 15.dp).zIndex(1f),
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