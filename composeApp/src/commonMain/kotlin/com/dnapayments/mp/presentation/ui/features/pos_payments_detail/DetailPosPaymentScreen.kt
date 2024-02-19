package com.dnapayments.mp.presentation.ui.features.pos_payments_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.main_screens.ScreenName
import com.dnapayments.mp.domain.model.online_payments.OnlinePaymentMethod
import com.dnapayments.mp.domain.model.pos_payments.PosPaymentCard
import com.dnapayments.mp.domain.model.transactions.pos.PosTransaction
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.greyFirst
import com.dnapayments.mp.presentation.theme.lightGrey
import com.dnapayments.mp.presentation.ui.common.ClipboardDotsContent
import com.dnapayments.mp.presentation.ui.common.DNADots
import com.dnapayments.mp.presentation.ui.common.DNAExpandBox
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.presentation.ui.common.DNATextWithIcon
import com.dnapayments.mp.presentation.ui.common.DNATopAppBar
import com.dnapayments.mp.presentation.ui.common.DefaultDotsContent
import com.dnapayments.mp.presentation.ui.common.PainterDotsContent
import com.dnapayments.mp.presentation.ui.common.PainterWithBackgroundDotsContent
import com.dnapayments.mp.utils.constants.Constants
import com.dnapayments.mp.utils.extension.changePlatformColor
import com.dnapayments.mp.utils.extension.toMoneyString
import com.dnapayments.mp.utils.firebase.logEvent
import com.dnapayments.mp.utils.navigation.LocalNavigator
import com.dnapayments.mp.utils.navigation.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

class DetailPosPaymentScreen(private val posTransaction: PosTransaction) : Screen {

    @Composable
    override fun Content() {

        changePlatformColor(true)

        LifecycleEffect(
            onStarted = {
                logEvent(
                    Constants.SCREEN_OPEN_EVENT,
                    mapOf(Constants.SCREEN_NAME to ScreenName.DETAIL_POS_PAYMENT)
                )
            }
        )

        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(greyFirst),
            verticalArrangement = Arrangement.Top
        ) {
            DNATopAppBar(
                title = stringResource(MR.strings.payment_detail),
                navigationIcon = painterResource(MR.images.ic_green_arrow_back),
                navigationText = stringResource(MR.strings.close),
                onNavigationClick = {
                    navigator.pop()
                }
            )
            PosPaymentDetailContent(
                modifier = Modifier.wrapContentHeight(),
                transaction = posTransaction
            )
        }
    }

    @Composable
    private fun PosPaymentDetailContent(
        modifier: Modifier,
        transaction: PosTransaction
    ) {
        Column(
            modifier.fillMaxWidth().verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(Paddings.medium))
            DNAText(
                text = transaction.amount.toMoneyString(transaction.currency),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = DnaTextStyle.Bold32
            )
            Spacer(modifier = Modifier.height(Paddings.medium))
            DNATextWithIcon(
                modifier = modifier.align(Alignment.CenterHorizontally)
                    .padding(vertical = Paddings.extraSmall),
                text = transaction.status.displayName,
                style = DnaTextStyle.WithAlphaNormal12,
                icon = transaction.status.imageResource,
                textColor = transaction.status.textColor,
                backgroundColor = transaction.status.backgroundColor,
            )
            Spacer(modifier = Modifier.height(Paddings.standard12dp))
            DNATextWithIcon(
                modifier = modifier.align(Alignment.CenterHorizontally),
                text = transaction.status.detailText ?: "",
                style = DnaTextStyle.WithAlphaNormal14,
                icon = MR.images.ic_message
            )
            Spacer(modifier = Modifier.height(Paddings.large))
            PaymentDetails(modifier, transaction)
            Divider(
                modifier = Modifier.fillMaxWidth().padding(horizontal = Paddings.medium),
                color = lightGrey
            )
            TransactionDetails(modifier, transaction)
        }
    }

    @Composable
    private fun PaymentDetails(
        modifier: Modifier,
        transaction: PosTransaction
    ) {
        val clipboardManager: ClipboardManager = LocalClipboardManager.current
        DNAExpandBox(
            modifier,
            MR.strings.payment_detail,
            MR.images.ic_card,
            true,
        ) {
            DNADots {
                DefaultDotsContent(
                    title = MR.strings.date,
                    value = transaction.transactionDate
                )
            }
            DNADots {
                ClipboardDotsContent(
                    MR.strings.transaction_id,
                    transaction.transactionId,
                    clipboardManager
                )
            }
            DNADots(isContinued = false) {
                PainterWithBackgroundDotsContent(
                    MR.strings.transaction_type,
                    transaction.transactionType.value,
                    transaction.transactionType.imageResource,
                    transaction.transactionType.backgroundColor
                )
            }
        }
    }


    @Composable
    private fun TransactionDetails(
        modifier: Modifier,
        transaction: PosTransaction
    ) {
        val clipboardManager: ClipboardManager = LocalClipboardManager.current
        val type = PosPaymentCard.fromCardScheme(transaction.cardScheme)
        val captureMethod = when (transaction.captureMethod) {
            "Pay by Bank app" -> OnlinePaymentMethod.PAY_BY_BANK
            "Open Banking" -> OnlinePaymentMethod.OPEN_BANK
            "Klarna" -> OnlinePaymentMethod.KLARNA
            else -> OnlinePaymentMethod.UNDEFINED
        }
        DNAExpandBox(
            modifier,
            MR.strings.details,
            MR.images.ic_comment,
            false,
        ) {
            DNADots {
                DefaultDotsContent(
                    MR.strings.transaction_details,
                    transaction.transactionDetails
                )
            }
            DNADots {
                DefaultDotsContent(
                    MR.strings.mid,
                    transaction.mid
                )
            }
            DNADots {
                ClipboardDotsContent(
                    MR.strings.terminal_id,
                    transaction.terminalId,
                    clipboardManager
                )
            }
            DNADots {
                PainterDotsContent(
                    title = MR.strings.card,
                    value = transaction.cardMask.ifEmpty {
                        stringResource(captureMethod.stringResource)
                    },
                    icon = if (type != PosPaymentCard.UNDEFINED || captureMethod != OnlinePaymentMethod.UNDEFINED) {
                        type.imageResource ?: captureMethod.imageResource
                    } else {
                        null
                    }
                )
            }
            DNADots {
                DefaultDotsContent(
                    MR.strings.card_type,
                    transaction.cardType
                )
            }
            DNADots(isContinued = false) {
                DefaultDotsContent(
                    MR.strings.card_category,
                    stringResource(
                        when (transaction.isCorporateCard) {
                            true -> MR.strings.corporate
                            false -> MR.strings.personal
                            null -> MR.strings.not_applicable
                        }
                    )
                )
            }
        }
    }
}