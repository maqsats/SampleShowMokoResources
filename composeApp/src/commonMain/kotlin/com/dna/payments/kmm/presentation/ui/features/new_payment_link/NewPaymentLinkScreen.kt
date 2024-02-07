package com.dna.payments.kmm.presentation.ui.features.new_payment_link

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen
import dev.icerock.moko.resources.compose.stringResource

class NewPaymentLinkScreen : DrawerScreen {

    override val isFilterEnabled: Boolean = false

    @Composable
    override fun Content() {
    }

    @Composable
    override fun DrawerContent(isToolbarCollapsed: Boolean) {
        Column {
            Spacer(modifier = Modifier.height(Paddings.large))
            CreatePaymentLinkForm(
                title = "Store"
            ) {

            }

            CreatePaymentLinkForm(
                title = "Order number"
            ) {

            }

            CreatePaymentLinkForm(
                title = "Amount"
            ) {

            }

            CreatePaymentLinkForm(
                title = "Customer name"
            ) {

            }

            CreatePaymentLinkForm(
                title = "Description"
            ) {

            }

            CreatePaymentLinkForm(
                title = "Expiry date"
            ) {

            }
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
            bottom = Paddings.extraLarge
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