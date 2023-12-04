package com.dna.payments.kmm.presentation.ui.features.payment_methods

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.payment_methods.PaymentMethod
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.greyColor
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.features.payment_methods_detail.DetailPaymentMethodsScreen
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

class PaymentMethodsScreen : DrawerScreen {
    override val key: ScreenKey = uniqueScreenKey
    override val isFilterEnabled: Boolean = false

    @Composable
    override fun DrawerContent() {
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            PaymentMethodsContent(
                modifier = Modifier.wrapContentHeight(),
                onItemClicked = {
                    navigator.push(DetailPaymentMethodsScreen(it))
                }
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }
    }

    @Composable
    override fun DrawerHeader() {
        Column {
            Spacer(modifier = Modifier.height(24.dp))
            DNAText(
                text = stringResource(MR.strings.payment_methods),
                style = DnaTextStyle.Bold20,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }

    @Composable
    override fun DrawerFilter() {

    }

    @Composable
    private fun PaymentMethodsContent(
        modifier: Modifier = Modifier,
        onItemClicked: (PaymentMethod) -> Unit
    ) {
        Column(
            modifier = modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = modifier.height(24.dp))
            Column {
                PaymentMethodDataFactory.getPaymentMethods().forEach {
                    PaymentMethodsItem(paymentMethod = it, onItemClicked = onItemClicked)
                }
            }
        }
    }

    @Composable
    private fun PaymentMethodsItem(
        modifier: Modifier = Modifier,
        paymentMethod: PaymentMethod,
        onItemClicked: (PaymentMethod) -> Unit
    ) {
        Box(
            modifier = modifier.fillMaxWidth().wrapContentHeight().padding(8.dp)
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(
                    Color.White,
                    RoundedCornerShape(8.dp)
                ).noRippleClickable { onItemClicked(paymentMethod) }
        )
        {
            Row(
                modifier = modifier.fillMaxWidth().fillMaxHeight().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = modifier
                            .border(
                                BorderStroke(width = 1.dp, color = greyColor),
                                shape = RoundedCornerShape(4.dp)
                            ).height(36.dp)
                            .background(Color.White)
                            .width(36.dp).padding(4.dp),
                    ) {
                        Icon(
                            painter = painterResource(paymentMethod.icon),
                            contentDescription = stringResource(paymentMethod.title),
                            tint = Color.Unspecified
                        )
                    }
                    DNAText(
                        modifier = modifier.padding(start = 16.dp),
                        style = DnaTextStyle.SemiBold16,
                        text = stringResource(paymentMethod.title)
                    )
                }
                Icon(
                    painter = painterResource(MR.images.ic_next_arrow),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}