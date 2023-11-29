package com.dna.payments.kmm.presentation.ui.features.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen
import dev.icerock.moko.resources.compose.painterResource

class OverviewScreen : DrawerScreen {

    override val isFilterEnabled = true

    @Composable
    override fun DrawerContent() { // Just for testing purposes
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            items (100) {
                Text(
                    text = "Item $it",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }

    @Composable
    override fun DrawerHeader() {   // Just for testing purposes
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
                    text = "Overview",
                    style = DnaTextStyle.Bold22,
                )
            }
        }
    }

    @Composable
    override fun DrawerFilter() {  // Just for testing purposes
        Image(
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.CenterStart,
            painter = painterResource(MR.images.header),
            contentDescription = null
        )
    }
}