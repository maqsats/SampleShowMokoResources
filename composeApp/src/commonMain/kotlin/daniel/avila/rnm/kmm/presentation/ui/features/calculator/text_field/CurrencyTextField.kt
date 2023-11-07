package daniel.avila.rnm.kmm.presentation.ui.features.calculator.text_field

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import daniel.avila.rnm.kmm.MR
import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.domain.model.exchange_rate.BuyOrSell
import daniel.avila.rnm.kmm.presentation.ui.common.RoundedBackground
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.currency.CurrencyContract
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.currency.CurrencyViewModel
import dev.icerock.moko.resources.compose.painterResource

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