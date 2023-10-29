package daniel.avila.rnm.kmm.presentation.ui.features.national_bank_currency

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency
import daniel.avila.rnm.kmm.utils.extension.formatMoney


@Composable
fun NationalBankCurrencyHeader(nationalBankCurrency: NationalBankCurrency) {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter("https://img.freepik.com/premium-vector/usa-america-national-official-flag-symbol-banner-vector-illustration_622428-3866.jpg"),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(54.dp).height(40.dp).padding(5.dp)
                .clip(RoundedCornerShape(3.dp))
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = nationalBankCurrency.currencyName,
                style = MaterialTheme.typography.h4,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = nationalBankCurrency.currencyCode,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = nationalBankCurrency.rate.formatMoney() + " KZT • Cегодня • 15 октября 2023",
                style = MaterialTheme.typography.h2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}