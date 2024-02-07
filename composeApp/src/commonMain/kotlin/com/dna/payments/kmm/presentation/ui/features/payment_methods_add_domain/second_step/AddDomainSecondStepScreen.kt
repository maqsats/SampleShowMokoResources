package com.dna.payments.kmm.presentation.ui.features.payment_methods_add_domain.second_step

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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.main_screens.ScreenName
import com.dna.payments.kmm.domain.model.payment_methods.PaymentMethod
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DNAGreenBackButton
import com.dna.payments.kmm.presentation.ui.common.DNAOutlinedGreenButton
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNAYellowButton
import com.dna.payments.kmm.presentation.ui.features.payment_methods_add_domain.third_step.AddDomainThirdStepScreen
import com.dna.payments.kmm.utils.constants.Constants
import com.dna.payments.kmm.utils.firebase.logEvent
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

class AddDomainSecondStepScreen(
    private val domain: String,
    private val paymentMethod: PaymentMethod
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        LifecycleEffect(
            onStarted = {
                logEvent(
                    Constants.SCREEN_OPEN_EVENT,
                    mapOf(Constants.SCREEN_NAME to ScreenName.ADD_DOMAIN_SECOND_STEP)
                )
            }
        )

        val navigator = LocalNavigator.currentOrThrow
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = Paddings.medium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DNAGreenBackButton(
                    text = stringResource(MR.strings.close),
                    onClick = { navigator.pop() },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                DNAText(
                    text = stringResource(MR.strings.add_domain),
                    modifier = Modifier.padding(horizontal = Paddings.xxLarge),
                    style = DnaTextStyle.Normal16
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    DNAText(
                        text = stringResource(MR.strings.download_associated),
                        modifier = Modifier.padding(
                            start = Paddings.medium,
                            top = Paddings.small,
                            end = Paddings.medium,
                        ),
                        style = DnaTextStyle.SemiBold20
                    )
                    Spacer(modifier = Modifier.height(Paddings.normal))
                    DNAOutlinedGreenButton(
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                horizontal = Paddings.medium,
                            ),
                        content = {
                            Row {
                                Row(modifier = Modifier.wrapContentWidth().wrapContentHeight()) {
                                    Icon(
                                        modifier = Modifier.align(Alignment.CenterVertically),
                                        painter = painterResource(MR.images.ic_download),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                    Spacer(modifier = Modifier.width(Paddings.standard))
                                    DNAText(
                                        text = stringResource(MR.strings.download),
                                        style = DnaTextStyle.GreenMedium16
                                    )
                                }
                            }
                        },
                        onClick = {},

                        )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .padding(bottom = Paddings.normal),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DNAOutlinedGreenButton(
                        modifier = Modifier
                            .weight(0.4f)
                            .padding(
                                start = Paddings.medium,
                            ),
                        content = {
                            Row(
                                modifier = Modifier.wrapContentWidth().wrapContentHeight()
                                    .padding(vertical = Paddings.small),
                            ) {
                                Icon(
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                        .height(24.dp).width(24.dp),
                                    painter = painterResource(MR.images.ic_guide_arrow_back),
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                )
                                Spacer(modifier = Modifier.width(Paddings.small))
                                DNAText(
                                    text = stringResource(MR.strings.back),
                                    style = DnaTextStyle.GreenMedium16
                                )
                            }
                        },
                        onClick = { navigator.pop() },
                    )
                    DNAYellowButton(
                        text = stringResource(MR.strings.next_step),
                        onClick = {
                            navigator.replace(
                                AddDomainThirdStepScreen(
                                    domain,
                                    paymentMethod
                                )
                            )
                        },
                        icon = MR.images.product_guide_arrow,
                        textColor = Color.Black,
                        screenName = ScreenName.ADD_DOMAIN_SECOND_STEP,
                        modifier = Modifier.weight(0.6f).padding(
                            horizontal = Paddings.medium
                        )
                    )
                }
            }
        }
    }
}

