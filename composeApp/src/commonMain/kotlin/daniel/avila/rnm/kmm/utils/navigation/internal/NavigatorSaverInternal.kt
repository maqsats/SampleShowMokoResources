package daniel.avila.rnm.kmm.utils.navigation.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import daniel.avila.rnm.kmm.utils.navigation.LocalNavigatorSaver
import daniel.avila.rnm.kmm.utils.navigation.Navigator
import daniel.avila.rnm.kmm.utils.navigation.NavigatorDisposeBehavior

internal val LocalNavigatorStateHolder: ProvidableCompositionLocal<SaveableStateHolder> =
    staticCompositionLocalOf { error("LocalNavigatorStateHolder not initialized") }

@OptIn(InternalVoyagerApi::class)
@Composable
internal fun rememberNavigator(
    screens: List<Screen>,
    key: String,
    disposeBehavior: NavigatorDisposeBehavior,
    parent: Navigator?
): Navigator {
    val stateHolder = LocalNavigatorStateHolder.current
    val navigatorSaver = LocalNavigatorSaver.current
    val saver = remember(navigatorSaver, stateHolder, parent, disposeBehavior) {
        navigatorSaver.saver(screens, key, stateHolder, disposeBehavior, parent)
    }

    return rememberSaveable(saver = saver, key = key) {
        Navigator(screens, key, stateHolder, disposeBehavior, parent)
    }
}