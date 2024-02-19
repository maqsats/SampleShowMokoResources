package com.dnapayments.mp.utils.navigation.internal

import androidx.compose.runtime.Composable
import com.dnapayments.mp.utils.navigation.BackHandler
import com.dnapayments.mp.utils.navigation.Navigator
import com.dnapayments.mp.utils.navigation.OnBackPressed

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