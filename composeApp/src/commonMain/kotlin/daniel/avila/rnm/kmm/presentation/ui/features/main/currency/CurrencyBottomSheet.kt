package daniel.avila.rnm.kmm.presentation.ui.features.main.currency

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.presentation.ui.common.LocalBottomSheetNavigator


class CurrencyBottomSheet(
    private val currencyList: List<Currency>,
    private val onCurrencySelected: (Currency) -> Unit
) : Screen {

    @Composable
    override fun Content() {

        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        LazyColumn(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(10.dp)
        ) {
            items(currencyList) {
                Box(modifier = Modifier.fillMaxWidth().height(50.dp).padding(10.dp).clickable {
                    bottomSheetNavigator.hide()
                    onCurrencySelected(it)
                }) {
                    Text(text = it.name, style = MaterialTheme.typography.button)
                }
            }
        }
    }
}
