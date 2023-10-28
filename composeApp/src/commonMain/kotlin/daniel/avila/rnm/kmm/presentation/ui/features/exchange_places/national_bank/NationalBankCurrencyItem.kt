package daniel.avila.rnm.kmm.presentation.ui.features.exchange_places.national_bank

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBank
import daniel.avila.rnm.kmm.utils.extension.formatMoney


@Composable
fun NationalBankCurrencyItem(it: NationalBank) {
    Row(
        modifier = Modifier.wrapContentWidth().wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter("https://stakingcrypto.info/static/assets/coins/euro-logo.png"),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp).padding(5.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = it.currencyCode,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = it.rate.formatMoney(),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
    Spacer(modifier = Modifier.width(10.dp))
}
