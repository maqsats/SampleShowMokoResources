package daniel.avila.rnm.kmm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import daniel.avila.rnm.kmm.presentation.theme.TengeTodayTheme
import daniel.avila.rnm.kmm.presentation.ui.common.shouldUseSwipeBack
import daniel.avila.rnm.kmm.presentation.ui.features.home.HomeScreen
import daniel.avila.rnm.kmm.utils.navigation.Navigator
import daniel.avila.rnm.kmm.utils.swipable.SlideTransition
import daniel.avila.rnm.kmm.utils.swipable.VoyagerSwipeBackContent

@Composable
internal fun App() = TengeTodayTheme {
    Navigator(HomeScreen()) { navigator ->
        val supportSwipeBack = remember { shouldUseSwipeBack }

        if (supportSwipeBack) {
            VoyagerSwipeBackContent(navigator)
        } else {
            SlideTransition(navigator)
        }
    }
}

