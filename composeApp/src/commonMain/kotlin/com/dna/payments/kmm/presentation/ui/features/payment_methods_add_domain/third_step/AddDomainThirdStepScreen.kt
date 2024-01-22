package com.dna.payments.kmm.presentation.ui.features.payment_methods_add_domain.third_step

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.payment_methods.PaymentMethod
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.greyColorBackground
import com.dna.payments.kmm.presentation.theme.greyFirst
import com.dna.payments.kmm.presentation.ui.common.DNAGreenBackButton
import com.dna.payments.kmm.presentation.ui.common.DNAOutlinedGreenButton
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNAYellowButton
import com.dna.payments.kmm.presentation.ui.common.UiStateController
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.NavigatorResultString
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import com.dna.payments.kmm.utils.navigation.popWithResult
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest

class AddDomainThirdStepScreen(
    private val domain: String,
    private val paymentMethod: PaymentMethod
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<AddDomainThirdStepViewModel>()
        val state by viewModel.uiState.collectAsState()
        val clipboardManager: ClipboardManager = LocalClipboardManager.current
        val hostProtocol: String = stringResource(MR.strings.host_protocol)

        UiStateController(state.addDomain)

        LaunchedEffect(key1 = Unit) {
            viewModel.effect.collectLatest { effect ->
                when (effect) {
                    AddDomainThirdStepContract.Effect.OnRegisterNewDomainSuccess -> {
                        navigator.popWithResult(
                            "12345",
                            NavigatorResultString(
                                true
                            )
                        )
                    }
                }
            }
        }

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
                        text = stringResource(MR.strings.host_domain),
                        modifier = Modifier.padding(
                            start = Paddings.medium,
                            top = Paddings.small,
                            end = Paddings.medium,
                        ),
                        style = DnaTextStyle.SemiBold20
                    )
                    Spacer(modifier = Modifier.height(Paddings.small))
                    DNAText(
                        text = stringResource(MR.strings.host_verification),
                        modifier = Modifier.padding(
                            start = Paddings.medium,
                            top = Paddings.small,
                            end = Paddings.medium,
                        ),
                        style = DnaTextStyle.Normal14
                    )
                    Spacer(modifier = Modifier.height(Paddings.normal))
                    Box(
                        modifier = Modifier
                            .padding(horizontal = Paddings.medium)
                            .shadow(1.dp, shape = RoundedCornerShape(8.dp))
                            .border(
                                BorderStroke(width = 1.dp, color = greyColorBackground),
                                shape = RoundedCornerShape(8.dp)
                            ).height(36.dp)
                            .background(greyFirst)
                            .fillMaxWidth().padding(vertical = Paddings.extraSmall),

                        ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .noRippleClickable {
                                    clipboardManager.setText(AnnotatedString(hostProtocol))
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            DNAText(
                                stringResource(MR.strings.host_protocol),
                                style = DnaTextStyle.GreenMedium14,
                                modifier = Modifier.padding(start = Paddings.small).weight(0.9f),
                                maxLines = 1
                            )
                            Icon(
                                painter = painterResource(MR.images.ic_copy),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.height(24.dp).width(24.dp).weight(0.1f)
                            )
                        }
                    }
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
                        text = stringResource(MR.strings.add),
                        onClick = {
                            viewModel.setEvent(
                                AddDomainThirdStepContract.Event.OnAddNewDomain(
                                    domain,
                                    paymentMethod.type
                                )
                            )
                        },
                        textColor = Color.Black,
                        modifier = Modifier.weight(0.6f).padding(
                            horizontal = Paddings.medium
                        )
                    )
                }
            }
        }
    }
}

