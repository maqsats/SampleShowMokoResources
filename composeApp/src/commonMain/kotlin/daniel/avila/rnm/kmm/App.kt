package daniel.avila.rnm.kmm

import androidx.compose.runtime.Composable
import daniel.avila.rnm.kmm.presentation.theme.TengeTodayTheme
import daniel.avila.rnm.kmm.presentation.ui.features.home.HomeScreen
import daniel.avila.rnm.kmm.utils.navigation.Navigator
import daniel.avila.rnm.kmm.utils.swipable.VoyagerSwipeBackContent

@Composable
internal fun App() = TengeTodayTheme {
    Navigator(HomeScreen()) { navigator ->
        VoyagerSwipeBackContent(navigator)
    }
}

