package daniel.avila.rnm.kmm.presentation.ui.features.all_places.national_bank

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.MR
import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.ui.common.LoadingView
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun NationalBankScreen(
    nationalBankCurrencyListUiState: ResourceUiState<List<NationalBankCurrency>>
) {
    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(MR.images.bank),
                modifier = Modifier.width(24.dp).height(24.dp),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = "НацБанк РК",
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(MR.images.menu),
                modifier = Modifier.width(24.dp).height(24.dp).clickable {

                },
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyRow(modifier = Modifier.weight(1f).wrapContentHeight()) {
                when (nationalBankCurrencyListUiState) {
                    is ResourceUiState.Success -> {
                        items(nationalBankCurrencyListUiState.data) {
                            NationalBankCurrencyItem(it)
                        }
                    }
                    else -> {
                        item {
                            LoadingView(
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                cornerBorderSize = 5.dp
                            )
                        }
                    }
                }
            }
            Text(
                modifier = Modifier.wrapContentWidth().wrapContentHeight()
                    .padding(horizontal = 10.dp),
                textAlign = TextAlign.Center,
                text = "Все",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.surface
            )
        }
    }
}

