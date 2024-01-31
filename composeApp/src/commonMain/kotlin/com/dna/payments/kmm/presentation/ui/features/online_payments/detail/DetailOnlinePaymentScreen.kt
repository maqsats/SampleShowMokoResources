package com.dna.payments.kmm.presentation.ui.features.online_payments.detail

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.online_payments.OnlinePaymentMethod
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentCard
import com.dna.payments.kmm.domain.model.transactions.Transaction
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.greyFirst
import com.dna.payments.kmm.presentation.ui.common.ClipboardDotsContent
import com.dna.payments.kmm.presentation.ui.common.DNADots
import com.dna.payments.kmm.presentation.ui.common.DNAExpandBox
import com.dna.payments.kmm.presentation.ui.common.DNAGreenBackButton
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNATextWithIcon
import com.dna.payments.kmm.presentation.ui.common.DefaultDotsContent
import com.dna.payments.kmm.presentation.ui.common.PainterDotsContent
import com.dna.payments.kmm.presentation.ui.common.PainterWithBackgroundDotsContent
import com.dna.payments.kmm.utils.extension.changePlatformColor
import com.dna.payments.kmm.utils.extension.toMoneyString
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import dev.icerock.moko.resources.compose.stringResource

class DetailOnlinePaymentScreen(private val transaction: Transaction) : Screen {
    @Composable
    override fun Content() {

        changePlatformColor(true)

        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(greyFirst),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = Paddings.medium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DNAGreenBackButton(
                    text = stringResource(MR.strings.close),
                    onClick = {
                        navigator.pop()
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                DNAText(
                    text = stringResource(MR.strings.payment_detail),
                    modifier = Modifier.padding(horizontal = Paddings.extraLarge),
                    style = DnaTextStyle.Normal16
                )
            }
            PosPaymentDetailContent(
                modifier = Modifier.wrapContentHeight(),
                transaction = transaction
            )
        }
    }

    @Composable
    private fun PosPaymentDetailContent(
        modifier: Modifier,
        transaction: Transaction
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
                text = stringResource(transaction.status.stringResource),
                style = DnaTextStyle.WithAlphaNormal12,
                icon = transaction.status.icon,
                textColor = transaction.status.textColor,
                backgroundColor = transaction.status.backgroundColor
            )
            Spacer(modifier = Modifier.height(Paddings.standard12dp))
            DNATextWithIcon(
                modifier = modifier.align(Alignment.CenterHorizontally),
                text = transaction.description,
                style = DnaTextStyle.WithAlphaNormal14,
                icon = MR.images.ic_message
            )
            Spacer(modifier = Modifier.height(Paddings.large))
            PaymentDetails(modifier, transaction)
            Divider(modifier = Modifier.fillMaxWidth().padding(horizontal = Paddings.medium))
            VerificationDetails(modifier, transaction)
            Divider(modifier = Modifier.fillMaxWidth().padding(horizontal = Paddings.medium))
            SummaryDetails(modifier, transaction)
        }
    }

    @Composable
    private fun PaymentDetails(
        modifier: Modifier,
        transaction: Transaction
    ) {
        val clipboardManager: ClipboardManager = LocalClipboardManager.current
        DNAExpandBox(
            modifier,
            MR.strings.payment_detail,
            MR.images.ic_card,
            true,
        ) {
            DNADots {
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
            }
            DNADots {
                DefaultDotsContent(
                    title = MR.strings.charged_amount,
                    value = transaction.currency + " " + transaction.amount.toString()
                )
            }
            DNADots {
                DefaultDotsContent(
                    title = MR.strings.date,
                    value = transaction.createdDate
                )
            }
            DNADots {
                ClipboardDotsContent(
                    MR.strings.id,
                    transaction.id,
                    clipboardManager
                )
            }
            DNADots {
                ClipboardDotsContent(
                    MR.strings.order_number,
                    transaction.invoiceId,
                    clipboardManager
                )
            }
            DNADots {
                ClipboardDotsContent(
                    MR.strings.rnr,
                    transaction.reference,
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
    private fun VerificationDetails(
        modifier: Modifier,
        transaction: Transaction
    ) {
        DNAExpandBox(
            modifier,
            MR.strings.verification,
            MR.images.ic_verified_detail,
            isFirst = false
        ) {
            DNADots {
                DefaultDotsContent(
                    MR.strings.three_d_secure_short,
                    transaction.secure3D.toString()
                )
            }
            DNADots {
                DefaultDotsContent(
                    MR.strings.avs_house_number,
                    transaction.avsHouseNumberResult
                )
            }
            DNADots {
                DefaultDotsContent(
                    MR.strings.csc_result,
                    transaction.cscResult
                )
            }
            DNADots {
                DefaultDotsContent(
                    MR.strings.pa_enrollment,
                    transaction.paEnrollment
                )
            }
            DNADots(isContinued = false) {
                DefaultDotsContent(
                    MR.strings.pa_authentication,
                    transaction.paAuthentication
                )
            }
        }
    }

    @Composable
    private fun SummaryDetails(
        modifier: Modifier,
        transaction: Transaction
    ) {
        DNAExpandBox(
            modifier,
            MR.strings.summary,
            MR.images.ic_bill,
            isFirst = false
        ) {
            DNADots {
                DefaultDotsContent(
                    MR.strings.processing_type,
                    transaction.processingTypeName
                )
            }
            DNADots {
                DefaultDotsContent(
                    MR.strings.store,
                    transaction.shop ?: ""
                )
            }
            DNADots {
                DefaultDotsContent(
                    MR.strings.description,
                    transaction.description
                )
            }
            DNADots {
                DefaultDotsContent(
                    MR.strings.authorised_on,
                    transaction.authDate,
                )
            }
            DNADots {
                DefaultDotsContent(
                    MR.strings.issuer,
                    transaction.issuer,
                )
            }
            DNADots {
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
            }
            DNADots {
                DefaultDotsContent(
                    MR.strings.acquirer_response_code,
                    transaction.acquirerResponseCode,
                )
            }
            DNADots(isContinued = false) {
                DefaultDotsContent(
                    MR.strings.auth_code,
                    transaction.authCode
                )
            }
        }
    }
}
