package com.dnapayments.mp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.dnapayments.mp.presentation.theme.AppTheme
import com.dnapayments.mp.presentation.ui.features.nav_auth.NavAuthScreen
import com.dnapayments.mp.utils.navigation.Navigator
import com.dnapayments.mp.utils.navigation.NavigatorDisposeBehavior
import com.dnapayments.mp.utils.navigation.shouldUseSwipeBack
import com.dnapayments.mp.utils.swipable.SlideTransition
import com.dnapayments.mp.utils.swipable.VoyagerSwipeBackContent

@Composable
internal fun App() = AppTheme {
    Navigator(
        NavAuthScreen(),
        disposeBehavior = NavigatorDisposeBehavior(
            disposeNestedNavigators = false,
            disposeSteps = false
        )
    ) { navigator ->
        val supportSwipeBack = remember { shouldUseSwipeBack }
        if (supportSwipeBack) {
            VoyagerSwipeBackContent(navigator)
        } else {
            SlideTransition(navigator)
        }
    }
}

