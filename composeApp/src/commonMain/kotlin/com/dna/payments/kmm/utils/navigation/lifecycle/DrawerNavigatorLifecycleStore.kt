package com.dna.payments.kmm.utils.navigation.lifecycle

import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.concurrent.ThreadSafeMap
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerNavigator
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public object DrawerNavigatorLifecycleStore {

    private val owners = ThreadSafeMap<NavigatorKey, ThreadSafeMap<KType, DrawerNavigatorDisposable>>()

    /**
     * Register a NavigatorDisposable that will be called `onDispose` on the
     * [navigator] leaves the Composition.
     */
    @ExperimentalVoyagerApi
    public inline fun <reified T : DrawerNavigatorDisposable> register(
        navigator: DrawerNavigator,
        noinline factory: (NavigatorKey) -> T
    ): T {
        return register(navigator, typeOf<T>(), factory) as T
    }

    @OptIn(InternalVoyagerApi::class)
    @PublishedApi
    internal fun <T : DrawerNavigatorDisposable> register(
        navigator: DrawerNavigator,
        screenDisposeListenerType: KType,
        factory: (NavigatorKey) -> T
    ): DrawerNavigatorDisposable {
        return owners.getOrPut(navigator.key) {
            ThreadSafeMap<KType, DrawerNavigatorDisposable>().apply {
                put(screenDisposeListenerType, factory(navigator.key))
            }
        }.getOrPut(screenDisposeListenerType) {
            factory(navigator.key)
        }
    }

    @OptIn(InternalVoyagerApi::class)
    @ExperimentalVoyagerApi
    public fun remove(navigator: DrawerNavigator) {
        owners.remove(navigator.key)?.forEach { it.value.onDispose(navigator) }
    }
}