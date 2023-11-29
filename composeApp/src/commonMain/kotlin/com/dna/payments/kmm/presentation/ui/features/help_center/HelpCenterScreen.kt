package com.dna.payments.kmm.presentation.ui.features.help_center

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import com.dna.payments.kmm.presentation.ui.common.DNAGreenBackButton
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow

class HelpCenterScreen : Screen {

    @Composable
    override fun Content() {  // Just for testing purposes
        val parentNavigator = LocalNavigator.currentOrThrow

        Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
            DNAGreenBackButton(
                "Back",
                onClick = {
                    parentNavigator.pop()
                }
            )
        }
    }
}