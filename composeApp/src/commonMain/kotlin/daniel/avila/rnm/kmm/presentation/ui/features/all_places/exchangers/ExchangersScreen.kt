package daniel.avila.rnm.kmm.presentation.ui.features.all_places.exchangers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.MR
import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.domain.model.exchanger.ExchangerTab
import daniel.avila.rnm.kmm.domain.model.exchanger.ExchangerType
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.state.ManagementResourceUiState
import daniel.avila.rnm.kmm.presentation.ui.features.all_places.AllPlacesContract
import daniel.avila.rnm.kmm.presentation.ui.features.all_places.AllPlacesViewModel
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun ExchangersScreen(
    exchangerListUiState: ResourceUiState<List<ExchangeRate>>,
    allPlacesViewModel: AllPlacesViewModel,
) {
    val list = listOf(
        ExchangerTab("Все", true, exchangerType = ExchangerType.ALL),
        ExchangerTab("Банки", false, exchangerType = ExchangerType.BANK),
        ExchangerTab("Обменники", false, exchangerType = ExchangerType.EXCHANGERS),
    )

    val selectedExchangerTab = remember { mutableStateOf(list.first()) }

    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
            .padding(start = 10.dp, end = 10.dp, top = 40.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(MR.images.exchange),
                modifier = Modifier.width(24.dp).height(24.dp),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = "Пункты обмена",
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

        Spacer(modifier = Modifier.height(15.dp))

        ExchangersTab(
            modifier = Modifier.fillMaxWidth(),
            list,
            selectedExchangerTab
        )

        Spacer(modifier = Modifier.height(10.dp))

        ManagementResourceUiState(
            modifier = Modifier.fillMaxSize(),
            resourceUiState = exchangerListUiState,
            successView = { exchangerList ->
                Column {
                    exchangerList.forEach { exchanger ->
                        ExchangerItem(
                            exchanger,
                            isFirst = exchanger == exchangerList.first(),
                            onClick = {

                            })
                    }
                }
            },
            onTryAgain = { allPlacesViewModel.setEvent(AllPlacesContract.Event.OnTryCheckAgainClick) },
            onCheckAgain = { allPlacesViewModel.setEvent(AllPlacesContract.Event.OnTryCheckAgainClick) },
        )
    }
}
