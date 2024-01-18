package com.dna.payments.kmm.utils.navigation.internal

import androidx.compose.runtime.Composable
import com.dna.payments.kmm.utils.navigation.BackHandler
import com.dna.payments.kmm.utils.navigation.Navigator
import com.dna.payments.kmm.utils.navigation.OnBackPressed

@Composable
internal fun NavigatorBackHandler(
    navigator: Navigator,
    onBackPressed: OnBackPressed
) {
    if (onBackPressed != null) {
        BackHandler(
            enabled = navigator.canPop || navigator.parent?.canPop ?: false,
            onBack = {
                if (onBackPressed(navigator.lastItem)) {
                    if (navigator.pop().not()) {
                        navigator.parent?.pop()
                    }
                }
            }
        )
    }
}