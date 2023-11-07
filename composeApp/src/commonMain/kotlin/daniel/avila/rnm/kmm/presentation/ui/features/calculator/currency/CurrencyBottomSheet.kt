package daniel.avila.rnm.kmm.presentation.ui.features.calculator.currency


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.presentation.state.ManagementResourceUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyBottomSheet(
    currencyViewModel: CurrencyViewModel,
    bottomSheetState: SheetState,
    currencyPair: MutableState<Pair<Currency, Currency>>,
    state: CurrencyContract.State,
    openBottomSheet: MutableState<Boolean>
) {
    val scope = rememberCoroutineScope()

    ManagementResourceUiState(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().background(Color.White),
        resourceUiState = state.currencies,
        successView = { currencyList ->
            LazyColumn(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                items(currencyList) {
                    Column(modifier = Modifier.clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = {
                            currencyPair.value = Pair(currencyPair.value.first, it)
                            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                if (!bottomSheetState.isVisible) openBottomSheet.value = false
                            }
                        }
                    )) {
                        Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp)
                        Row(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                .padding(vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberImagePainter(it.currencyLogo),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(15.dp)
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            Column(verticalArrangement = Arrangement.Center) {
                                Text(text = it.name, style = MaterialTheme.typography.button)
                                Text(text = it.code, style = MaterialTheme.typography.h6)
                            }
                        }
                    }
                }
            }
        },
        onTryAgain = { currencyViewModel.setEvent(CurrencyContract.Event.OnTryCheckAgainClick) },
        onCheckAgain = { currencyViewModel.setEvent(CurrencyContract.Event.OnTryCheckAgainClick) },
    )
}