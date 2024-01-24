package com.dna.payments.kmm.presentation.ui.features.pos_payments_detail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.online_payments.OnlinePaymentMethod
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentCard
import com.dna.payments.kmm.domain.model.transactions.pos.PosTransaction
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.greyColorBackground
import com.dna.payments.kmm.presentation.theme.greyFirst
import com.dna.payments.kmm.presentation.ui.common.DNAGreenBackButton
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNATextWithIcon
import com.dna.payments.kmm.utils.extension.changePlatformColor
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.extension.toMoneyString
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlin.math.roundToInt

class DetailPosPaymentScreen(private val posTransaction: PosTransaction) : Screen {
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
                modifier = modifier.align(Alignment.CenterHorizontally),
                text = transaction.status.displayName,
                style = DnaTextStyle.WithAlphaNormal12,
                icon = transaction.status.imageResource,
                textColor = transaction.status.textColor,
                backgroundColor = transaction.status.backgroundColor
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
            Divider(modifier = Modifier.fillMaxWidth().padding(horizontal = Paddings.medium))
            TransactionDetails(modifier, transaction)
        }
    }

    @Composable
    private fun PaymentDetails(
        modifier: Modifier,
        transaction: PosTransaction
    ) {
        var isExpanded by remember { mutableStateOf<Boolean?>(true) }
        var currentRotation by remember { mutableStateOf(0f) }
        val rotation = remember { Animatable(currentRotation) }
        val clipboardManager: ClipboardManager = LocalClipboardManager.current

        Box(
            modifier.fillMaxWidth().wrapContentHeight().background(Color.White)
                .padding(vertical = Paddings.large, horizontal = Paddings.medium)
                .animateContentSize()
                .noRippleClickable {
                    isExpanded = isExpanded != true
                }
        ) {
            LaunchedEffect(isExpanded)
            {
                if (isExpanded == null) return@LaunchedEffect
                rotation.animateTo(
                    targetValue = currentRotation + 180f,
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotation = value
                }
            }
            Column {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = modifier
                                .shadow(1.dp, shape = RoundedCornerShape(4.dp))
                                .border(
                                    BorderStroke(width = 1.dp, color = greyColorBackground),
                                    shape = RoundedCornerShape(4.dp)
                                ).height(40.dp)
                                .background(Color.White)
                                .width(40.dp).padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(MR.images.ic_card),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }
                        DNAText(
                            text = stringResource(MR.strings.payment_detail),
                            modifier = Modifier.padding(horizontal = Paddings.medium),
                            style = DnaTextStyle.Normal16
                        )
                    }
                    Icon(
                        painter = painterResource(MR.images.ic_arrow_expanded_up),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.rotate(rotation.value)
                    )
                }
                Spacer(modifier = Modifier.height(Paddings.small))
                if (isExpanded == true) {
                    Spacer(modifier = Modifier.height(Paddings.small))
                    Row {
                        Icon(
                            painter = painterResource(MR.images.dots),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.padding(start = Paddings.medium)
                        )
                        Column(
                            modifier = Modifier.padding(start = Paddings.extraLarge)
                        ) {
                            DNAText(
                                stringResource(MR.strings.date),
                                style = DnaTextStyle.WithAlpha12
                            )
                            DNAText(transaction.transactionDate, style = DnaTextStyle.Normal16)
                            Spacer(modifier = Modifier.height(Paddings.large))
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .noRippleClickable {
                                        clipboardManager.setText(AnnotatedString(transaction.transactionId))
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically

                            ) {
                                Column(modifier = Modifier.wrapContentHeight()) {
                                    DNAText(
                                        stringResource(MR.strings.transaction_id),
                                        style = DnaTextStyle.WithAlpha12
                                    )
                                    DNAText(
                                        transaction.transactionId,
                                        style = DnaTextStyle.Normal16,
                                        maxLines = 1,
                                        modifier = Modifier.width(220.dp)
                                    )
                                }
                                Icon(
                                    painter = painterResource(MR.images.ic_copy),
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.height(24.dp).width(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(Paddings.large))
                            DNAText(
                                stringResource(MR.strings.transaction_type),
                                style = DnaTextStyle.WithAlpha12
                            )

                            if (transaction.transactionType.imageResource != null) {
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(top = Paddings.extraSmall),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                transaction.transactionType.backgroundColor,
                                                CircleShape
                                            )
                                            .size(24.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(transaction.transactionType.imageResource),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(Paddings.extraSmall)
                                                .height(24.dp)
                                                .width(24.dp)
                                        )
                                    }
                                    DNAText(
                                        transaction.transactionType.value,
                                        style = DnaTextStyle.Normal16,
                                        modifier = Modifier.padding(start = Paddings.small)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TransactionDetails(
        modifier: Modifier,
        transaction: PosTransaction
    ) {
        var isExpanded by remember { mutableStateOf<Boolean?>(null) }
        var currentRotation by remember { mutableStateOf(0f) }
        val rotation = remember { Animatable(currentRotation) }
        val clipboardManager: ClipboardManager = LocalClipboardManager.current
        val type = PosPaymentCard.fromCardScheme(transaction.cardScheme)
        val captureMethod = when (transaction.captureMethod) {
            "Pay by Bank app" -> OnlinePaymentMethod.PAY_BY_BANK
            "Open Banking" -> OnlinePaymentMethod.OPEN_BANK
            "Klarna" -> OnlinePaymentMethod.KLARNA
            else -> OnlinePaymentMethod.UNDEFINED
        }

        Box(
            modifier.fillMaxWidth().wrapContentHeight().background(Color.White)
                .padding(vertical = Paddings.large, horizontal = Paddings.medium)
                .animateContentSize()
                .noRippleClickable {
                    isExpanded = isExpanded != true
                }
        ) {
            LaunchedEffect(isExpanded)
            {
                if (isExpanded == null) return@LaunchedEffect
                rotation.animateTo(
                    targetValue = currentRotation + 180f,
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotation = value
                }
            }
            Column {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = modifier
                                .shadow(1.dp, shape = RoundedCornerShape(4.dp))
                                .border(
                                    BorderStroke(width = 1.dp, color = greyColorBackground),
                                    shape = RoundedCornerShape(4.dp)
                                ).height(40.dp)
                                .background(Color.White)
                                .width(40.dp).padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(MR.images.ic_comment),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }
                        DNAText(
                            text = stringResource(MR.strings.details),
                            modifier = Modifier.padding(horizontal = Paddings.medium),
                            style = DnaTextStyle.Normal16
                        )
                    }
                    Icon(
                        painter = painterResource(MR.images.ic_arrow_expanded_up),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.rotate(rotation.value)
                    )
                }
                Spacer(modifier = Modifier.height(Paddings.small))
                if (isExpanded == true) {
                    Spacer(modifier = Modifier.height(Paddings.small))
                    Row {
                        Icon(
                            painter = painterResource(MR.images.dots_six),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.padding(start = Paddings.medium)
                        )
                        Column(
                            modifier = Modifier.padding(start = Paddings.extraLarge)
                        ) {
                            DNAText(
                                stringResource(MR.strings.transaction_details),
                                style = DnaTextStyle.WithAlpha12
                            )
                            DNAText(transaction.transactionDetails, style = DnaTextStyle.Normal16)
                            Spacer(modifier = Modifier.height(Paddings.large))
                            DNAText(
                                stringResource(MR.strings.mid),
                                style = DnaTextStyle.WithAlpha12
                            )
                            DNAText(transaction.mid, style = DnaTextStyle.Normal16)
                            Spacer(modifier = Modifier.height(Paddings.large))
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .noRippleClickable {
                                        clipboardManager.setText(AnnotatedString(transaction.transactionId))
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically

                            ) {
                                Column(modifier = Modifier.wrapContentHeight()) {
                                    DNAText(
                                        stringResource(MR.strings.terminal_id),
                                        style = DnaTextStyle.WithAlpha12
                                    )
                                    DNAText(
                                        transaction.terminalId,
                                        style = DnaTextStyle.Normal16,
                                        maxLines = 1,
                                        modifier = Modifier.width(220.dp)
                                    )
                                }
                                Icon(
                                    painter = painterResource(MR.images.ic_copy),
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.height(24.dp).width(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(Paddings.large))
                            DNAText(
                                stringResource(MR.strings.card),
                                style = DnaTextStyle.WithAlpha12
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(top = Paddings.extraSmall)
                            ) {
                                if (type != PosPaymentCard.UNDEFINED || captureMethod != OnlinePaymentMethod.UNDEFINED) {
                                    type.imageResource?.let {
                                        Icon(
                                            painter = painterResource(it),
                                            contentDescription = null,
                                            tint = Color.Unspecified,
                                            modifier = Modifier.height(24.dp).width(24.dp)
                                        )
                                    } ?: captureMethod.imageResource?.let {
                                        Icon(
                                            painter = painterResource(it),
                                            contentDescription = null,
                                            tint = Color.Unspecified,
                                            modifier = Modifier.height(24.dp).width(24.dp)
                                        )
                                    }
                                    DNAText(
                                        modifier = modifier.padding(start = Paddings.small),
                                        style = DnaTextStyle.Normal16,
                                        text = transaction.cardMask.ifEmpty {
                                            stringResource(captureMethod.stringResource)
                                        }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(Paddings.large))
                            DNAText(
                                stringResource(MR.strings.card_type),
                                style = DnaTextStyle.WithAlpha12
                            )
                            DNAText(
                                transaction.cardType.ifEmpty { stringResource(MR.strings.not_applicable) },
                                style = DnaTextStyle.Normal16
                            )
                            Spacer(modifier = Modifier.height(Paddings.large))
                            DNAText(
                                stringResource(MR.strings.card_category),
                                style = DnaTextStyle.WithAlpha12
                            )
                            DNAText(
                                text = stringResource(
                                    when (transaction.isCorporateCard) {
                                        true -> MR.strings.corporate
                                        false -> MR.strings.personal
                                        null -> MR.strings.not_applicable
                                    }
                                ), style = DnaTextStyle.Normal16
                            )
                            Spacer(modifier = Modifier.height(Paddings.large))
                        }
                    }
                }
            }
        }
    }
}