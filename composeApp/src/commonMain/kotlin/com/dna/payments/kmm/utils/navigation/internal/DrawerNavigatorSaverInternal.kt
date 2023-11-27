package com.dna.payments.kmm.utils.navigation.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import com.dna.payments.kmm.utils.navigation.LocalDrawerNavigatorSaver
import com.dna.payments.kmm.utils.navigation.NavigatorDisposeBehavior
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerNavigator
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen

internal val LocalDrawerNavigatorStateHolder: ProvidableCompositionLocal<SaveableStateHolder> =
    staticCompositionLocalOf { error("LocalNavigatorStateHolder not initialized") }

@OptIn(InternalVoyagerApi::class)
@Composable
internal fun rememberDrawerNavigator(
    screens: List<DrawerScreen>,
    key: String,
    disposeBehavior: NavigatorDisposeBehavior,
    parent: DrawerNavigator?
): DrawerNavigator {
    val stateHolder = LocalDrawerNavigatorStateHolder.current
    val navigatorSaver = LocalDrawerNavigatorSaver.current
    val saver = remember(navigatorSaver, stateHolder, parent, disposeBehavior) {
        navigatorSaver.saver(screens, key, stateHolder, disposeBehavior, parent)
    }

    return rememberSaveable(saver = saver, key = key) {
        DrawerNavigator(screens, key, stateHolder, disposeBehavior, parent)
    }
}