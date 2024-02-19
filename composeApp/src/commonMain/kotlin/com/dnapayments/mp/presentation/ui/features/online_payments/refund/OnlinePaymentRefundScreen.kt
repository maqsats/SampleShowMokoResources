package com.dnapayments.mp.presentation.ui.features.online_payments.refund

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.transactions.Transaction
import com.dnapayments.mp.presentation.model.TextFieldUiState
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.greyFirst
import com.dnapayments.mp.presentation.theme.lightGrey
import com.dnapayments.mp.presentation.theme.white
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.presentation.ui.common.DNATextWithIcon
import com.dnapayments.mp.presentation.ui.common.DNATopAppBar
import com.dnapayments.mp.presentation.ui.common.DNAYellowButton
import com.dnapayments.mp.presentation.ui.common.DefaultDotsContent
import com.dnapayments.mp.presentation.ui.common.DnaTextField
import com.dnapayments.mp.presentation.ui.common.UiStateController
import com.dnapayments.mp.utils.extension.changePlatformColor
import com.dnapayments.mp.utils.extension.toMoneyString
import com.dnapayments.mp.utils.navigation.LocalNavigator
import com.dnapayments.mp.utils.navigation.OnlinePaymentNavigatorResult
import com.dnapayments.mp.utils.navigation.OnlinePaymentNavigatorResultType
import com.dnapayments.mp.utils.navigation.currentOrThrow
import com.dnapayments.mp.utils.navigation.popWithResult
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.parameter.parametersOf

class OnlinePaymentRefundScreen(private val transaction: Transaction) : Screen {
    @Composable
    override fun Content() {

        changePlatformColor(true)
        val onlinePaymentRefundViewModel = getScreenModel<OnlinePaymentRefundViewModel> {
            parametersOf(transaction.balance)
        }
        val state by onlinePaymentRefundViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            onlinePaymentRefundViewModel.setEvent(
                OnlinePaymentRefundContract.Event.OnInit(
                    amount = transaction.amount,
                    balance = transaction.balance
                )
            )

            onlinePaymentRefundViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is OnlinePaymentRefundContract.Effect.OnSuccessfullyRefunded -> {
                        navigator.popWithResult(
                            OnlinePaymentNavigatorResult(
                                OnlinePaymentNavigatorResultType.REFUND,
                                effect.id
                            )
                        )
                    }
                }
            }
        }
        UiStateController(state.refundState)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(greyFirst),
            verticalArrangement = Arrangement.Top
        ) {
            DNATopAppBar(
                title = stringResource(MR.strings.payment_refund),
                navigationIcon = painterResource(MR.images.ic_green_arrow_back),
                navigationText = stringResource(MR.strings.close),
                onNavigationClick = {
                    navigator.pop()
                }
            )
            OnlinePaymentRefundContent(
                modifier = Modifier,
                transaction = transaction,
                state = state,
                onSendRefundClicked = {
                    onlinePaymentRefundViewModel.setEvent(
                        OnlinePaymentRefundContract.Event.OnRefundClicked(
                            it
                        )
                    )
                },
            )
        }
    }

    @Composable
    private fun OnlinePaymentRefundContent(
        modifier: Modifier,
        transaction: Transaction,
        state: OnlinePaymentRefundContract.State,
        onSendRefundClicked: (String) -> Unit
    ) {
        Column(
            modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(Paddings.medium))
            DNAText(
                text = transaction.amount.toMoneyString(transaction.currency),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = DnaTextStyle.Bold32
            )
            Spacer(modifier = Modifier.height(Paddings.standard12dp))
            DNATextWithIcon(
                modifier = modifier.padding(start = Paddings.medium, end = Paddings.medium)
                    .align(Alignment.CenterHorizontally),
                text = transaction.description,
                style = DnaTextStyle.WithAlphaNormal14,
                icon = MR.images.ic_message
            )
            Spacer(modifier = Modifier.height(Paddings.large))
            Column(
                modifier = modifier.fillMaxSize().background(white)
                    .padding(top = Paddings.large, start = Paddings.medium, end = Paddings.medium),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    AmountField(
                        amount = state.amount
                    )
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = lightGrey
                    )
                    Spacer(modifier = Modifier.height(Paddings.medium))
                    DefaultDotsContent(
                        title = MR.strings.payment_amount,
                        value = transaction.amount.toMoneyString(transaction.currency)
                    )
                    Spacer(modifier = Modifier.height(Paddings.medium))
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = lightGrey
                    )
                    Spacer(modifier = Modifier.height(Paddings.medium))
                    DefaultDotsContent(
                        title = MR.strings.balance,
                        value = transaction.balance.toMoneyString(transaction.currency)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .padding(bottom = Paddings.normal),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DNAYellowButton(
                        enabled = state.isButtonEnabled.value,
                        content = {
                            DNAText(
                                text = stringResource(MR.strings.refund),
                                style = DnaTextStyle.Medium16,
                                modifier = Modifier.padding(vertical = Paddings.extraSmall)
                            )
                        },
                        onClick = {
                            onSendRefundClicked(transaction.id)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    @Composable
    private fun AmountField(
        amount: TextFieldUiState
    ) {
        DNAText(
            text = stringResource(MR.strings.refund_amount),
            style = DnaTextStyle.Medium16,
        )
        Spacer(modifier = Modifier.height(Paddings.small))
        DnaTextField(
            amount, stringResource(MR.strings.charge_amount),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = {
                DNAText(
                    style = DnaTextStyle.Normal16,
                    text = stringResource(MR.strings.euro),
                    modifier = Modifier.padding(start = Paddings.standard12dp)
                )
            })
    }
}