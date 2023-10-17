package daniel.avila.rnm.kmm.utils.navigation.internal

import androidx.compose.runtime.Composable
import daniel.avila.rnm.kmm.presentation.ui.common.BackHandler
import daniel.avila.rnm.kmm.utils.navigation.Navigator
import daniel.avila.rnm.kmm.utils.navigation.OnBackPressed

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