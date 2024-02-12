package com.dna.payments.kmm.presentation.ui.features.new_payment_link

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.main_screens.ScreenName
import com.dna.payments.kmm.presentation.theme.Dimens
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNAYellowButton
import com.dna.payments.kmm.presentation.ui.common.DnaTextField
import com.dna.payments.kmm.presentation.ui.features.help_center.HelpCenterScreen
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

class NewPaymentLinkScreen : DrawerScreen {

    override val isFilterEnabled: Boolean = false

    @Composable
    override fun Content() {
    }

    @Composable
    override fun DrawerContent(isToolbarCollapsed: Boolean) {
        val newPaymentViewModel = getScreenModel<NewPaymentLinkViewModel>()
        val state by newPaymentViewModel.uiState.collectAsState()
        val parentNavigator = LocalNavigator.currentOrThrow

        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(modifier = Modifier.height(Paddings.large))

            CreatePaymentLinkForm(stringResource(MR.strings.store)) {
                DnaTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textState = state.store,
                    placeholder = stringResource(MR.strings.select_store),
                    enabled = false,
                    onClick = {
                        parentNavigator.push(HelpCenterScreen())
                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier
                                .size(Dimens.iconSize),
                            painter = painterResource(MR.images.ic_arrow_down),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                )
            }

            CreatePaymentLinkForm(stringResource(MR.strings.order_number)) {
                DnaTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textState = state.orderNumber,
                    placeholder = stringResource(MR.strings.order_number),
                    trailingIcon = {
                        Icon(
                            modifier = Modifier
                                .size(Dimens.iconSize).noRippleClickable {
                                    newPaymentViewModel.setEvent(NewPaymentLinkContract.Event.OnGenerateNewRandomNumberClick)
                                },
                            painter = painterResource(MR.images.ic_re_create_link),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                )
            }

            CreatePaymentLinkForm(stringResource(MR.strings.amount)) {
                DnaTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textState = state.amount,
                    placeholder = stringResource(MR.strings.empty_amount)
                )
            }

            CreatePaymentLinkForm(stringResource(MR.strings.customer_name)) {
                DnaTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textState = state.customerName,
                    placeholder = stringResource(MR.strings.name)
                )
            }

            CreatePaymentLinkForm(stringResource(MR.strings.description)) {
                DnaTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textState = state.description,
                    placeholder = stringResource(MR.strings.name_of_service_or_item)
                )
            }

            CreatePaymentLinkForm(stringResource(MR.strings.link_expiry)) {
                DnaTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textState = state.expiredDate,
                    placeholder = stringResource(MR.strings.select_date)
                )
            }

            DNAYellowButton(
                modifier = Modifier.padding(
                    start = Paddings.medium,
                    end = Paddings.medium,
                    bottom = Paddings.large
                ),
                text = stringResource(MR.strings.create_link),
                onClick = { },
                enabled = false,
                screenName = ScreenName.CREATE_LINK
            )
        }
    }

    @Composable
    override fun DrawerHeader() {
        Column {
            Spacer(modifier = Modifier.height(Paddings.large))
            DNAText(
                text = stringResource(MR.strings.new_payment_link),
                style = DnaTextStyle.Bold20,
                modifier = Modifier.padding(
                    horizontal = Paddings.medium,
                    vertical = Paddings.standard
                )
            )
        }
    }

    @Composable
    override fun DrawerFilter() {

    }
}

@Composable
private fun CreatePaymentLinkForm(
    title: String,
    isCompulsory: Boolean = true,
    hint: String? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.padding(
            start = Paddings.medium,
            end = Paddings.medium,
            bottom = Paddings.large
        )
    ) {
        Row {
            if (isCompulsory) {
                DNAText(
                    text = "* ",
                    style = DnaTextStyle.MediumRed16
                )
            }
            DNAText(
                text = title,
                style = DnaTextStyle.Medium16Grey5
            )
        }
        Spacer(
            modifier = Modifier.height(Paddings.extraSmall)
        )
        content()
    }
}