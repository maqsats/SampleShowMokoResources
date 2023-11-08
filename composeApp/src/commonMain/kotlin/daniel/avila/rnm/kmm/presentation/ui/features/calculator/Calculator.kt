package daniel.avila.rnm.kmm.presentation.ui.features.calculator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import daniel.avila.rnm.kmm.domain.model.currency.CurrencyHelper.getDefaultCurrencyPair
import daniel.avila.rnm.kmm.domain.model.exchange_rate.BuyOrSellTab
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.ui.common.BottomSheet
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.currency.CurrencyBottomSheet
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.currency.CurrencyContract
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.currency.CurrencyViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.exchange_list_main.ExchangeRateListMain
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.text_field.CurrencyTextField
import daniel.avila.rnm.kmm.presentation.ui.features.home.buy_sell_tab.BuySellTabBar
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
class Calculator(val modifier: Modifier = Modifier) : Screen {

    @Composable
    override fun Content() {
        var selectedTab by remember { mutableStateOf(BuyOrSellTab("", false)) }

        val inputText = rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(
                TextFieldValue(
                    "100",
                    TextRange(3)
                )
            )
        }

        val openBottomSheet = rememberSaveable { mutableStateOf(false) }

        val controller = LocalSoftwareKeyboardController.current

        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false
        )

        val currencyPair = rememberSaveable { mutableStateOf(getDefaultCurrencyPair()) }

        val currencyViewModel = getScreenModel<CurrencyViewModel>()

        val state by currencyViewModel.uiState.collectAsState()

        var alreadyUpdatedCurrencyPair by rememberSaveable { mutableStateOf(true) }

        LaunchedEffect(state.currencies) {
            if (!alreadyUpdatedCurrencyPair) return@LaunchedEffect
            when (val currencyList = state.currencies) {
                is ResourceUiState.Success -> {
                    if (currencyList.data.size < 2)
                        return@LaunchedEffect
                    currencyPair.value =
                        currencyPair.value.copy(currencyList.data[0], currencyList.data[1])
                    alreadyUpdatedCurrencyPair = false
                }
                else -> {}
            }
        }

        LaunchedEffect(key1 = Unit) {
            currencyViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is CurrencyContract.Effect.NavigateToCurrencyDialog -> {
                        openBottomSheet.value = true
                    }
                }
            }
        }

        Column(
            modifier = modifier.fillMaxWidth().clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { controller?.hide() }
        ) {

            Spacer(modifier = Modifier.height(6.dp))

            BuySellTabBar(modifier = Modifier.fillMaxWidth()) {
                selectedTab = it
            }

            Spacer(modifier = Modifier.height(5.dp))

            CurrencyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .wrapContentHeight(),
                inputText,
                currencyViewModel,
                currencyPair,
                selectedTab.buyOrSell
            )

            Spacer(modifier = Modifier.height(5.dp))

            ExchangeRateListMain(
                modifier = Modifier.weight(1f),
                selectedTab.buyOrSell,
                inputText.value.text,
                currencyPair.value
            ).Content()
        }

        if (openBottomSheet.value) {
            BottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = { openBottomSheet.value = false }
            ) {
                CurrencyBottomSheet(
                    currencyViewModel,
                    bottomSheetState,
                    currencyPair,
                    state,
                    openBottomSheet
                )
            }
        }
    }
}
