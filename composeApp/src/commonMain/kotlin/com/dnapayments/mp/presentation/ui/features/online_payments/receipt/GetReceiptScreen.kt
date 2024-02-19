package com.dnapayments.mp.presentation.ui.features.online_payments.receipt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.online_payments.OnlinePaymentMethod
import com.dnapayments.mp.domain.model.pos_payments.PosPaymentCard
import com.dnapayments.mp.domain.model.transactions.Transaction
import com.dnapayments.mp.presentation.model.TextFieldUiState
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.greyFirst
import com.dnapayments.mp.presentation.theme.lightGrey
import com.dnapayments.mp.presentation.theme.white
import com.dnapayments.mp.presentation.ui.common.DNAEmailTextField
import com.dnapayments.mp.presentation.ui.common.DNAOutlinedGreenButton
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.presentation.ui.common.DNATextWithIcon
import com.dnapayments.mp.presentation.ui.common.DNATopAppBar
import com.dnapayments.mp.presentation.ui.common.DNAYellowButton
import com.dnapayments.mp.presentation.ui.common.DefaultDotsContent
import com.dnapayments.mp.presentation.ui.common.PainterDotsContent
import com.dnapayments.mp.presentation.ui.common.UiStateController
import com.dnapayments.mp.utils.extension.changePlatformColor
import com.dnapayments.mp.utils.extension.toMoneyString
import com.dnapayments.mp.utils.navigation.LocalNavigator
import com.dnapayments.mp.utils.navigation.Navigator
import com.dnapayments.mp.utils.navigation.OnlinePaymentNavigatorResult
import com.dnapayments.mp.utils.navigation.OnlinePaymentNavigatorResultType
import com.dnapayments.mp.utils.navigation.currentOrThrow
import com.dnapayments.mp.utils.navigation.popWithResult
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest

class GetReceiptScreen(private val transaction: Transaction) : Screen {
    @Composable
    override fun Content() {

        changePlatformColor(true)
        val getReceiptViewModel = getScreenModel<GetReceiptViewModel>()
        val state by getReceiptViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            getReceiptViewModel.effect.collectLatest { effect ->
                when (effect) {
                    GetReceiptContract.Effect.OnSendSuccess -> {
                        navigator.popWithResult(
                            OnlinePaymentNavigatorResult(
                                OnlinePaymentNavigatorResultType.SEND_RECEIPT,
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
                onSendReceiptClicked = {
                    getReceiptViewModel.setEvent(
                        GetReceiptContract.Event.OnSendClicked(
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
        state: GetReceiptContract.State,
        navigator: Navigator,
        onSendReceiptClicked: (String) -> Unit
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
                    EmailField(
                        email = state.email
                    )
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = lightGrey
                    )
                    Spacer(modifier = Modifier.height(Paddings.medium))
                    DefaultDotsContent(
                        title = MR.strings.date,
                        value = transaction.createdDate
                    )
                    Spacer(modifier = Modifier.height(Paddings.medium))
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = lightGrey
                    )
                    Spacer(modifier = Modifier.height(Paddings.medium))
                    PainterDotsContent(
                        title = MR.strings.payment_method,
                        value = when (transaction.paymentMethod) {
                            OnlinePaymentMethod.CARD -> {
                                transaction.cardMask
                            }

                            OnlinePaymentMethod.CLICK_TO_PAY -> {
                                transaction.cardMask
                            }

                            else -> {
                                stringResource(transaction.paymentMethod.stringResource)
                            }
                        },
                        icon = when (transaction.paymentMethod) {
                            OnlinePaymentMethod.CARD -> {
                                if (transaction.cardType != null) {
                                    PosPaymentCard.fromCardType(transaction.cardType).imageResource?.let {
                                        it
                                    }
                                } else {
                                    null
                                }
                            }

                            else -> {
                                transaction.paymentMethod.imageResource?.let {
                                    it
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(Paddings.medium))
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = lightGrey
                    )
                    Spacer(modifier = Modifier.height(Paddings.medium))
                    DefaultDotsContent(
                        MR.strings.order_number,
                        transaction.invoiceId
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .padding(bottom = Paddings.normal),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DNAOutlinedGreenButton(
                        enabled = state.isButtonEnabled.value,
                        modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                        content = {
                            Icon(
                                modifier = Modifier.align(Alignment.CenterVertically)
                                    .padding(vertical = Paddings.extraSmall)
                                    .height(24.dp).width(24.dp),
                                painter = painterResource(MR.images.ic_send_green),
                                contentDescription = null,
                                tint = Color.Unspecified,
                            )
                        },
                        onClick = { onSendReceiptClicked(transaction.id) },
                    )
                    DNAYellowButton(
                        enabled = state.isButtonEnabled.value,
                        content = {
                            DNAText(
                                text = stringResource(MR.strings.download_receipt),
                                style = DnaTextStyle.Medium16,
                                modifier = Modifier.padding(vertical = Paddings.extraSmall)
                            )
                        },
                        onClick = {},
                        modifier = Modifier.weight(1f).padding(
                            start = Paddings.medium
                        )
                    )
                }
            }
        }
    }

    @Composable
    private fun EmailField(
        email: TextFieldUiState
    ) {
        DNAText(
            text = stringResource(MR.strings.customer_email),
            style = DnaTextStyle.Medium16,
        )
        Spacer(modifier = Modifier.height(8.dp))
        DNAEmailTextField(email, stringResource(MR.strings.customer_email))
    }
}