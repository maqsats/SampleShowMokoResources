package daniel.avila.rnm.kmm.presentation.ui.features.all_places.exchangers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import daniel.avila.rnm.kmm.MR
import daniel.avila.rnm.kmm.domain.model.exchange_rate.CurrencyRate
import daniel.avila.rnm.kmm.utils.extension.formatMoney
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun CurrencyRateItem(currencyRate: CurrencyRate) {

    Column(modifier = Modifier.wrapContentHeight().fillMaxWidth()) {
        Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp)

        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(currencyRate.currencyLogo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(20.dp).height(15.dp)
            )

            Text(
                text = currencyRate.currencyCode,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )

            Image(
                painter = painterResource(MR.images.arrow_left),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(7.dp).height(8.dp)
            )

            Text(
                text = currencyRate.buy.formatMoney(),
                style = MaterialTheme.typography.h1,
                modifier = Modifier.weight(1f).padding(start = 3.dp)
            )

            Image(
                painter = painterResource(MR.images.arrow_right),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(7.dp).height(8.dp)
            )

            Text(
                text = currencyRate.sell.formatMoney(),
                style = MaterialTheme.typography.h1,
                modifier = Modifier.weight(1f).padding(start = 3.dp)
            )
        }
    }
}