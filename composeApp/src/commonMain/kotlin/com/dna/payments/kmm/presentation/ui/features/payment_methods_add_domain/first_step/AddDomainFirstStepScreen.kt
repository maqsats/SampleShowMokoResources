package com.dna.payments.kmm.presentation.ui.features.payment_methods_add_domain.first_step

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.payment_methods.PaymentMethod
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DNAGreenBackButton
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNAYellowButton
import com.dna.payments.kmm.presentation.ui.common.DnaTextField
import com.dna.payments.kmm.presentation.ui.features.payment_methods_add_domain.second_step.AddDomainSecondStepScreen
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import dev.icerock.moko.resources.compose.stringResource

class AddDomainFirstStepScreen(private val paymentMethod: PaymentMethod) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<AddDomainFirstStepViewModel>()
        val state by viewModel.uiState.collectAsState()
        val controller = LocalSoftwareKeyboardController.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .noRippleClickable {
                    controller?.hide()
                },
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
                        text = stringResource(MR.strings.add_domain_title),
                        modifier = Modifier.padding(
                            start = Paddings.medium,
                            top = Paddings.small,
                            end = Paddings.medium,
                        ),
                        style = DnaTextStyle.SemiBold20
                    )
                    DnaTextField(
                        textState = state.domain,
                        placeholder = stringResource(MR.strings.example),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = Paddings.medium,
                                top = Paddings.large,
                                end = Paddings.medium,
                            ),
                        leadingIcon = {
                            DNAText(
                                style = DnaTextStyle.Normal16,
                                text = stringResource(MR.strings.protocol),
                                modifier = Modifier.padding(start = Paddings.standard12dp)
                            )
                        }
                    )
                }
                DNAYellowButton(
                    text = stringResource(MR.strings.next_step),
                    onClick = {
                        navigator.push(
                            AddDomainSecondStepScreen(
                                state.domain.input.value,
                                paymentMethod
                            )
                        )
                    },
                    enabled = state.isNextEnabled.value,
                    icon = MR.images.product_guide_arrow,
                    textColor = Color.Black,
                    modifier = Modifier.padding(
                        start = Paddings.medium,
                        end = Paddings.medium,
                        bottom = Paddings.normal,
                    )
                )
            }
        }
    }
}

