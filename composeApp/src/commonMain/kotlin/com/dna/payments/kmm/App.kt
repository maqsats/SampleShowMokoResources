package com.dna.payments.kmm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.dna.payments.kmm.presentation.theme.AppTheme
import com.dna.payments.kmm.presentation.ui.common.shouldUseSwipeBack
import com.dna.payments.kmm.presentation.ui.features.characters.CharactersScreen
import com.dna.payments.kmm.utils.navigation.Navigator
import com.dna.payments.kmm.utils.swipable.SlideTransition
import com.dna.payments.kmm.utils.swipable.VoyagerSwipeBackContent

@Composable
internal fun App() = AppTheme {
    Navigator(CharactersScreen()) { navigator ->
        val supportSwipeBack = remember { shouldUseSwipeBack }

        if (supportSwipeBack) {
            VoyagerSwipeBackContent(navigator)
        } else {
            SlideTransition(navigator)
        }
    }
}

