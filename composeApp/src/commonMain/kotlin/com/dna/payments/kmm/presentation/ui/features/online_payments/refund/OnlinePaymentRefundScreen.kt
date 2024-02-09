package com.dna.payments.kmm.presentation.ui.features.online_payments.refund

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
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.transactions.Transaction
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.greyFirst
import com.dna.payments.kmm.presentation.theme.lightGrey
import com.dna.payments.kmm.presentation.theme.white
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNATextWithIcon
import com.dna.payments.kmm.presentation.ui.common.DNATopAppBar
import com.dna.payments.kmm.presentation.ui.common.DNAYellowButton
import com.dna.payments.kmm.presentation.ui.common.DefaultDotsContent
import com.dna.payments.kmm.presentation.ui.common.DnaTextField
import com.dna.payments.kmm.presentation.ui.common.UiStateController
import com.dna.payments.kmm.utils.extension.changePlatformColor
import com.dna.payments.kmm.utils.extension.toMoneyString
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.Navigator
import com.dna.payments.kmm.utils.navigation.OnlinePaymentNavigatorResult
import com.dna.payments.kmm.utils.navigation.OnlinePaymentNavigatorResultType
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import com.dna.payments.kmm.utils.navigation.popWithResult
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest

class OnlinePaymentRefundScreen(private val transaction: Transaction) : Screen {
    @Composable
    override fun Content() {

        changePlatformColor(true)
        val getReceiptViewModel = getScreenModel<OnlinePaymentRefundViewModel>()
        val state by getReceiptViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            getReceiptViewModel.setEvent(
                OnlinePaymentRefundContract.Event.OnInit(
                    amount = transaction.amount,
                    balance = transaction.balance
                )
            )

            getReceiptViewModel.effect.collectLatest { effect ->
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
        UiStateController(state.sendReceiptState)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(greyFirst),
            verticalArrangement = Arrangement.Top
        ) {
            DNATopAppBar(
                title = stringResource(MR.strings.get_receipt),
                navigationIcon = painterResource(MR.images.ic_green_arrow_back),
                navigationText = stringResource(MR.strings.close),
                onNavigationClick = {
                    navigator.pop()
                }
            )
            GetReceiptContent(
                modifier = Modifier,
                transaction = transaction,
                state = state,
                navigator = navigator,
                onSendRefundClicked = {
                    getReceiptViewModel.setEvent(
                        OnlinePaymentRefundContract.Event.OnRefundClicked(
                            it
                        )
                    )
                },
            )
        }
    }

    @Composable
    private fun GetReceiptContent(
        modifier: Modifier,
        transaction: Transaction,
        state: OnlinePaymentRefundContract.State,
        navigator: Navigator,
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