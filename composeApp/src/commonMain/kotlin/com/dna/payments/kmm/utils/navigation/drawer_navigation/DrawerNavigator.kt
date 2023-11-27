package com.dna.payments.kmm.utils.navigation.drawer_navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.concurrent.ThreadSafeMap
import cafe.adriel.voyager.core.concurrent.ThreadSafeSet
import cafe.adriel.voyager.core.lifecycle.MultipleProvideBeforeScreenContent
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleStore
import cafe.adriel.voyager.core.lifecycle.getNavigatorScreenLifecycleProvider
import cafe.adriel.voyager.core.lifecycle.rememberScreenLifecycleOwner
import cafe.adriel.voyager.core.model.ScreenModelStore
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.Stack
import cafe.adriel.voyager.core.stack.toMutableStateStack
import com.dna.payments.kmm.presentation.ui.common.DnaCollapsingToolbar
import com.dna.payments.kmm.utils.navigation.NavigatorDisposeBehavior
import com.dna.payments.kmm.utils.navigation.OnBackPressed
import com.dna.payments.kmm.utils.navigation.internal.ChildrenNavigationDisposableEffect
import com.dna.payments.kmm.utils.navigation.internal.DrawerNavigatorDisposableEffect
import com.dna.payments.kmm.utils.navigation.internal.DrawerStepDisposableEffect
import com.dna.payments.kmm.utils.navigation.internal.LocalDrawerNavigatorStateHolder
import com.dna.payments.kmm.utils.navigation.internal.rememberDrawerNavigator
import com.dna.payments.kmm.utils.navigation.lifecycle.NavigatorKey

typealias NavigatorContent = @Composable (navigator: DrawerNavigator) -> Unit


val LocalDrawerNavigator: ProvidableCompositionLocal<DrawerNavigator?> =
    staticCompositionLocalOf { null }

val <T> ProvidableCompositionLocal<T?>.currentOrThrow: T
    @Composable
    get() = current ?: error("CompositionLocal is null")

@Composable
fun CurrentDrawerScreen(drawerState: DrawerState) {
    val navigator = LocalDrawerNavigator.currentOrThrow
    val currentScreen = navigator.lastItem

    navigator.saveableState("currentDrawerScreen") {
        DnaCollapsingToolbar(
            drawerState = drawerState,
            isFilterEnabled = currentScreen.isFilterEnabled,
            headerContent = {
                currentScreen.DrawerHeader()
            },
            filterContent = {
                currentScreen.DrawerFilter()
            },
            content = {
                currentScreen.DrawerContent()
            }
        )
    }
}

@OptIn(InternalVoyagerApi::class)
@Composable
fun DrawerNavigator(
    drawerState: DrawerState,
    screen: DrawerScreen,
    disposeBehavior: NavigatorDisposeBehavior = NavigatorDisposeBehavior(),
    onBackPressed: OnBackPressed = { true },
    key: String = compositionUniqueId(),
    content: NavigatorContent = { CurrentDrawerScreen(drawerState) }
) {
    DrawerNavigator(
        drawerState = drawerState,
        screens = listOf(screen),
        disposeBehavior = disposeBehavior,
        onBackPressed = onBackPressed,
        key = key,
        content = content
    )
}

@OptIn(InternalVoyagerApi::class)
@Composable
fun DrawerNavigator(
    drawerState: DrawerState,
    screens: List<DrawerScreen>,
    disposeBehavior: NavigatorDisposeBehavior = NavigatorDisposeBehavior(),
    onBackPressed: OnBackPressed = { true },
    key: String = compositionUniqueId(),
    content: NavigatorContent = { CurrentDrawerScreen(drawerState) }
) {
    require(screens.isNotEmpty()) { "Navigator must have at least one screen" }
    require(key.isNotEmpty()) { "Navigator key can't be empty" }

    CompositionLocalProvider(
        LocalDrawerNavigatorStateHolder providesDefault rememberSaveableStateHolder()
    ) {
        val navigator =
            rememberDrawerNavigator(screens, key, disposeBehavior, LocalDrawerNavigator.current)

        if (navigator.parent?.disposeBehavior?.disposeNestedNavigators != false) {
            DrawerNavigatorDisposableEffect(navigator)
        }

        CompositionLocalProvider(
            LocalDrawerNavigator provides navigator
        ) {
            if (disposeBehavior.disposeSteps) {
                DrawerStepDisposableEffect(navigator)
            }

            content(navigator)
        }

        ChildrenNavigationDisposableEffect(navigator)
    }
}

public class DrawerNavigator @InternalVoyagerApi constructor(
    screens: List<DrawerScreen>,
    @InternalVoyagerApi public val key: String,
    private val stateHolder: SaveableStateHolder,
    public val disposeBehavior: NavigatorDisposeBehavior,
    public val parent: DrawerNavigator? = null
) : Stack<DrawerScreen> by screens.toMutableStateStack(minSize = 1) {

    public val level: Int =
        parent?.level?.inc() ?: 0

    public val lastItem: DrawerScreen by derivedStateOf {
        lastItemOrNull ?: error("Navigator has no screen")
    }

    private val stateKeys = ThreadSafeSet<String>()

    internal val children = ThreadSafeMap<NavigatorKey, DrawerNavigator>()

    @Deprecated(
        message = "Use 'lastItem' instead. Will be removed in 1.0.0.",
        replaceWith = ReplaceWith("lastItem")
    )
    public val last: Screen by derivedStateOf {
        lastItem
    }

    @OptIn(ExperimentalVoyagerApi::class, InternalVoyagerApi::class)
    @Composable
    public fun saveableState(
        key: String,
        screen: DrawerScreen = lastItem,
        content: @Composable () -> Unit
    ) {
        val stateKey = "${screen.key}:$key"
        stateKeys += stateKey

        @Composable
        fun provideSaveableState(suffixKey: String, content: @Composable () -> Unit) {
            val providedStateKey = "$stateKey:$suffixKey"
            stateKeys += providedStateKey
            stateHolder.SaveableStateProvider(providedStateKey, content)
        }

        val lifecycleOwner = rememberScreenLifecycleOwner(screen)
        val navigatorScreenLifecycleOwners = getNavigatorScreenLifecycleProvider(screen)

        val composed = remember(lifecycleOwner, navigatorScreenLifecycleOwners) {
            listOf(lifecycleOwner) + navigatorScreenLifecycleOwners
        }
        MultipleProvideBeforeScreenContent(
            screenLifecycleContentProviders = composed,
            provideSaveableState = { suffix, content -> provideSaveableState(suffix, content) },
            content = {
                stateHolder.SaveableStateProvider(stateKey, content)
            }
        )
    }

    public fun popUntilRoot() {
        popUntilRoot(this)
    }

    private tailrec fun popUntilRoot(navigator: DrawerNavigator) {
        navigator.popAll()

        if (navigator.parent != null) {
            popUntilRoot(navigator.parent)
        }
    }

    @InternalVoyagerApi
    public fun dispose(
        screen: Screen
    ) {
        ScreenModelStore.remove(screen)
        ScreenLifecycleStore.remove(screen)
        stateKeys
            .asSequence()
            .filter { it.startsWith(screen.key) }
            .forEach { key ->
                stateHolder.removeState(key)
                stateKeys -= key
            }
    }
}

@InternalVoyagerApi
@Composable
public fun compositionUniqueId(): String = currentCompositeKeyHash.toString(MaxSupportedRadix)

private val MaxSupportedRadix = 36