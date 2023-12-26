package com.dna.payments.kmm.presentation.ui.features.online_payments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen
import dev.icerock.moko.resources.compose.stringResource

class OnlinePaymentsScreen : DrawerScreen {
    override val isFilterEnabled = false

    @Composable
    override fun Content() {  // Just for testing purposes

    }

    @Composable
    override fun DrawerHeader() {  // Just for testing purposes
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                Modifier.fillMaxWidth().background(Color(0xFFF8F9F9)),
                contentAlignment = Alignment.CenterStart
            ) {
                DNAText(
                    modifier = Modifier.padding(
                        vertical = 10.dp,
                        horizontal = 16.dp
                    ),
                    text = stringResource(MR.strings.online_payments),
                    style = DnaTextStyle.Bold22,
                )
            }
        }
    }

    @Composable
    override fun DrawerFilter() {  // Just for testing purposes

    }
}