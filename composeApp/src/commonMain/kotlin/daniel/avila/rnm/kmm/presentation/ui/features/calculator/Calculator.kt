package daniel.avila.rnm.kmm.presentation.ui.features.calculator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.seiko.imageloader.rememberImagePainter
import daniel.avila.rnm.kmm.MR
import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.domain.model.currency.CurrencyHelper.getDefaultCurrencyPair
import daniel.avila.rnm.kmm.domain.model.exchange_rate.BuyOrSell
import daniel.avila.rnm.kmm.domain.model.exchange_rate.BuyOrSellTab
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.state.ManagementResourceUiState
import daniel.avila.rnm.kmm.presentation.ui.common.BottomSheet
import daniel.avila.rnm.kmm.presentation.ui.common.RoundedBackground
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.currency.CurrencyContract
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.currency.CurrencyViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.exchange_list_main.ExchangeRateListMain
import daniel.avila.rnm.kmm.presentation.ui.features.home.buy_sell_tab.BuySellTabBar
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
            println(currencyPair)
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

@Composable
fun CurrencyTextField(
    modifier: Modifier = Modifier,
    inputText: MutableState<TextFieldValue>,
    currencyViewModel: CurrencyViewModel,
    currencyPair: MutableState<Pair<Currency, Currency>>,
    buyOrSell: BuyOrSell
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            value = inputText.value,
            onValueChange = { input ->
                when {
                    input.text.length > 10 -> {

                    }
                    input.text.isEmpty() || input.text == "00" -> {
                        inputText.value = input.copy(text = "0", TextRange(1))
                    }
                    else -> inputText.value =
                        input.copy(input.text.filter { it.isDigit() }.trimStart('0'))
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                autoCorrect = false
            ),
            cursorBrush = Brush.verticalGradient(
                0.0f to Color.Transparent,
                0.15f to Color.Transparent,
                0.15f to Color.Black,
                0.85f to Color.Black,
                0.85f to Color.Transparent,
                1.00f to Color.Transparent
            ),
            maxLines = 1,
            textStyle = MaterialTheme.typography.body2.copy(textAlign = TextAlign.End),
        )

        Spacer(modifier = Modifier.width(10.dp))

        RoundedBackground(
            modifier = Modifier.wrapContentWidth(),
            height = 36.dp,
            paddingHorizontal = 8.dp,
            onClick = {
                currencyViewModel.setEvent(CurrencyContract.Event.OnCurrencyClick)
            }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberImagePainter(currencyPair.value.second.currencyLogo),
                    contentDescription = null,
                    modifier = Modifier
                        .width(20.dp)
                        .height(15.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = currencyPair.value.second.code,
                    style = MaterialTheme.typography.h6
                )

                Image(
                    painter = painterResource(MR.images.expand_more),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .width(14.dp)
                        .height(8.dp)
                )
            }
        }
    }


    Spacer(modifier = Modifier.height(5.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f).height(1.dp)
                .background(MaterialTheme.colors.secondary)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = when (buyOrSell) {
                BuyOrSell.BUY -> "Вы потратите".uppercase()
                BuyOrSell.SELL -> "Вы получите".uppercase()
            },
            style = MaterialTheme.typography.subtitle2
        )
    }
}


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
        modifier = Modifier.fillMaxWidth().wrapContentHeight().background(Color.White)
            .padding(horizontal = 20.dp),
        resourceUiState = state.currencies,
        successView = { currencyList ->
            LazyColumn(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().background(Color.White)
                    .padding(horizontal = 20.dp)
            ) {
                items(currencyList) {

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable(
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
