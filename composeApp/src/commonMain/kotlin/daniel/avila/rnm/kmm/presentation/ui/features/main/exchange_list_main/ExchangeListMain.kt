package daniel.avila.rnm.kmm.presentation.ui.features.main.exchange_list_main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.MR
import daniel.avila.rnm.kmm.presentation.ui.common.LocalBottomSheetNavigator
import daniel.avila.rnm.kmm.presentation.ui.common.RoundedBackground
import daniel.avila.rnm.kmm.presentation.ui.features.main.custom_main_tab.TabItem
import daniel.avila.rnm.kmm.presentation.ui.features.main.exchange.ExchangeItem
import daniel.avila.rnm.kmm.presentation.ui.features.main.exchange.ExchangeItemState
import daniel.avila.rnm.kmm.presentation.ui.features.screen2.Screen2
import daniel.avila.rnm.kmm.utils.navigation.LocalNavigator
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

@Composable
fun ExchangeListMain(modifier: Modifier) {
    val bottomSheetNavigator = LocalBottomSheetNavigator.current
    val current = LocalNavigator.current
    val list = listOf(
        TabItem("Ближайший", true),
        TabItem("Популярный", false)
    )

    var selectedItem by remember { mutableStateOf(list.first()) }

    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(MR.images.bank),
                    modifier = Modifier.width(20.dp).height(20.dp),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start,
                    text = stringResource(MR.strings.course_in_the_city),
                    style = MaterialTheme.typography.h4
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(MR.images.menu),
                    modifier = Modifier.width(20.dp).height(20.dp).clickable {
                        scope.launch {
                            bottomSheetNavigator.show(BottomSheet())
                        }
                    },
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(10.dp))


            RoundedBackground(
                modifier = Modifier.wrapContentWidth().padding(start = 15.dp),
                backgroundColor = MaterialTheme.colors.secondary,
                border = 45.dp,
                height = 22.dp,
                onClick = {

                }
            ) {
                Text(
                    text = "Самый выгодный",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSecondary,
                )
            }

            ExchangeItem(
                state = ExchangeItemState(
                    name = "Альфа-Банк",
                    priceTotal = "1 000 000 ₽",
                    location = "ул. Ленина, 1",
                    howFar = "1 км",
                    priceForOneDollar = "73.5 ₽"
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 15.dp),
            ) {
                list.forEach { tabItem ->
                    val isSelected = selectedItem == tabItem
                    RoundedBackground(
                        modifier = Modifier.wrapContentWidth(),
                        backgroundColor = if (isSelected) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.secondary,
                        border = 45.dp,
                        height = 22.dp,
                        onLongClick = {
                            current?.push(Screen2())
                            selectedItem = tabItem
                        },
                        onClick = {
                            selectedItem = tabItem
                        }
                    ) {
                        Text(
                            text = tabItem.stringResId,
                            style = MaterialTheme.typography.h5,
                            color = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary,
                        )
                    }

                    if (tabItem != list.last()) {
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }


        items(10) {
            ExchangeItem(
                state = ExchangeItemState(
                    name = "Альфа-Банк",
                    priceTotal = "1 000 000 ₽",
                    location = "ул. Ленина, 1",
                    howFar = "1 км",
                    priceForOneDollar = "73.5 ₽"
                )
            )
        }
    }
}