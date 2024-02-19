package com.dnapayments.mp.utils.navigation.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.DisposableEffectIgnoringConfiguration
import cafe.adriel.voyager.core.stack.StackEvent
import com.dnapayments.mp.utils.navigation.drawer_navigation.DrawerNavigator
import com.dnapayments.mp.utils.navigation.lifecycle.DrawerNavigatorLifecycleStore

private val disposableEvents: Set<StackEvent> =
    setOf(StackEvent.Pop, StackEvent.Replace)

@Composable
internal fun DrawerNavigatorDisposableEffect(
    navigator: DrawerNavigator
) {
    DisposableEffectIgnoringConfiguration(navigator) {
        onDispose {
            disposeDrawerNavigator(navigator)
        }
    }
}

@OptIn(InternalVoyagerApi::class)
@Composable
internal fun DrawerStepDisposableEffect(
    navigator: DrawerNavigator
) {
    val currentScreens = navigator.items

    DisposableEffect(currentScreens) {
        onDispose {
            val newScreenKeys = navigator.items.map { it.key }
            if (navigator.lastEvent in disposableEvents) {
                currentScreens.filter { it.key !in newScreenKeys }.forEach {
                    navigator.dispose(it)
                }
                navigator.clearEvent()
            }
        }
    }
}

@OptIn(InternalVoyagerApi::class)
@Composable
internal fun ChildrenNavigationDisposableEffect(
    navigator: DrawerNavigator
) {
    // disposing children navigators
    DisposableEffectIgnoringConfiguration(navigator) {
        onDispose {
            fun disposeChildren(navigator: DrawerNavigator) {
                disposeDrawerNavigator(navigator)
                navigator.children.values.forEach { childNavigator ->
                    disposeChildren(childNavigator)
                }
                navigator.children.clear()
            }
            if (navigator.parent == null || navigator.disposeBehavior.disposeNestedNavigators) {
                navigator.children.values.forEach { childNavigator ->
                    disposeChildren(childNavigator)
                }
            }
        }
    }

    // referencing nested navigators in parent navigator
    DisposableEffectIgnoringConfiguration(navigator) {
        navigator.parent?.children?.put(navigator.key, navigator)
        onDispose {
            if (navigator.parent?.disposeBehavior?.disposeNestedNavigators != false) {
                navigator.parent?.children?.remove(navigator.key)
            }
        }
    }
}

@OptIn(InternalVoyagerApi::class, ExperimentalVoyagerApi::class)
internal fun disposeDrawerNavigator(navigator: DrawerNavigator) {
    for (screen in navigator.items) {
        navigator.dispose(screen)
    }
    DrawerNavigatorLifecycleStore.remove(navigator)
    navigator.clearEvent()
}