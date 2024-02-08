package com.dna.payments.kmm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.dna.payments.kmm.presentation.theme.AppTheme
import com.dna.payments.kmm.presentation.ui.features.nav_auth.NavAuthScreen
import com.dna.payments.kmm.utils.navigation.Navigator
import com.dna.payments.kmm.utils.navigation.shouldUseSwipeBack
import com.dna.payments.kmm.utils.swipable.SlideTransition
import com.dna.payments.kmm.utils.swipable.VoyagerSwipeBackContent

@Composable
internal fun App() = AppTheme {
    Navigator(
        NavAuthScreen()
    ) { navigator ->
        val supportSwipeBack = remember { shouldUseSwipeBack }

        if (supportSwipeBack) {
            VoyagerSwipeBackContent(navigator)
        } else {
            SlideTransition(navigator)
        }
    }
}

