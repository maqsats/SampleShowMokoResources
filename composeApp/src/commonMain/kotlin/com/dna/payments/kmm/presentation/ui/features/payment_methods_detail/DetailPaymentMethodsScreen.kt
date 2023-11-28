package com.dna.payments.kmm.presentation.ui.features.payment_methods_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.payment_methods.PaymentMethod
import com.dna.payments.kmm.domain.model.payment_methods.setting.DetailTerminalSetting
import com.dna.payments.kmm.domain.model.payment_methods.setting.TerminalSetting
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.greyColor
import com.dna.payments.kmm.presentation.ui.common.DNAGreenBackButton
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

class DetailPaymentMethodsScreen(val paymentMethod: PaymentMethod) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {

        val controller = LocalSoftwareKeyboardController.current
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable {
                    controller?.hide()
                },
            verticalArrangement = Arrangement.Top
        ) {
            PaymentMethodsContent(
                modifier = Modifier.wrapContentHeight(),
                onItemClicked = {

                },
                onBackClicked = {
                    navigator.pop()
                }
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }
    }

    @Composable
    private fun PaymentMethodsContent(
        modifier: Modifier = Modifier,
        onItemClicked: (PaymentMethod) -> Unit,
        onBackClicked: () -> Unit,
    ) {
        Column(
            modifier = modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            DNAGreenBackButton(
                text = stringResource(MR.strings.back),
                onClick = onBackClicked,
                modifier = Modifier.padding(horizontal = 0.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = modifier
                        .border(
                            BorderStroke(width = 1.dp, color = greyColor),
                            shape = RoundedCornerShape(4.dp)
                        ).height(48.dp)
                        .background(Color.White)
                        .width(48.dp).padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(paymentMethod.icon),
                        contentDescription = stringResource(paymentMethod.title),
                        tint = Color.Unspecified,
                        modifier = modifier.height(40.dp).width(40.dp)
                    )
                }
                DNAText(
                    modifier = modifier.padding(start = 16.dp),
                    style = DnaTextStyle.SemiBold20,
                    text = stringResource(paymentMethod.title)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            DNAText(
                style = DnaTextStyle.Normal14,
                text = stringResource(paymentMethod.description)
            )
            Spacer(modifier = Modifier.height(24.dp))
            DNAText(
                style = DnaTextStyle.WithAlpha16,
                text = stringResource(MR.strings.terminals)
            )

        }
    }

    @Composable
    private fun TerminalSettingItem(
        modifier: Modifier = Modifier,
        terminalSetting: TerminalSetting,
        detailTerminalSettingList: List<DetailTerminalSetting>,
        onItemClicked: (PaymentMethod) -> Unit
    ) {
        Box(
            modifier = modifier.padding(8.dp).shadow(4.dp).background(
                Color.White,
                RoundedCornerShape(8.dp)
            ).fillMaxWidth().wrapContentHeight()
                .noRippleClickable { onItemClicked(paymentMethod) }
        ) {
            Row(
                modifier = modifier.fillMaxWidth().fillMaxHeight().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    DNAText(
                        text = terminalSetting.name
                    )
                    DNAText(
                        modifier = modifier.padding(start = 16.dp),
                        style = DnaTextStyle.Normal14,
                        text = stringResource(
                            MR.strings.count_terminals,
                            detailTerminalSettingList.size
                        )
                    )
                }
                Icon(
                    painter = painterResource(MR.images.ic_arrow_up),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}