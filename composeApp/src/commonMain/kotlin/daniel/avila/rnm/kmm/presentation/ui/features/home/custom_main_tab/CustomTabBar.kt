package daniel.avila.rnm.kmm.presentation.ui.features.home.custom_main_tab

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.domain.model.exchange_rate.BuyOrSell
import daniel.avila.rnm.kmm.presentation.ui.common.RoundedBackground

@Composable
fun CustomTabBar(modifier: Modifier, onTabSelected: (TabItem) -> Unit) {
    val list = listOf(
        TabItem("Купить", true),
        TabItem("Продать", false, buyOrSell = BuyOrSell.SELL)
    )
    LaunchedEffect(list) {
        onTabSelected(list.first())
    }
    var selectedItem by remember { mutableStateOf(list.first()) }
    Row(
        modifier = modifier
            .wrapContentHeight()
            .padding(horizontal = 15.dp),
    ) {
        list.forEach { tabItem ->
            val isSelected = selectedItem == tabItem
            RoundedBackground(
                modifier = Modifier.weight(1f),
                backgroundColor = if (isSelected) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.secondary,
                onClick = {
                    onTabSelected(tabItem)
                    selectedItem = tabItem
                }
            ) {
                Text(
                    text = tabItem.stringResId,
                    style = MaterialTheme.typography.caption,
                    color = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary,
                )
            }

            if (tabItem != list.last()) {
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}