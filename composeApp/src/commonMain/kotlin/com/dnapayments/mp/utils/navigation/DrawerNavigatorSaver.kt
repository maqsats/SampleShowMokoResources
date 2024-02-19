package com.dnapayments.mp.utils.navigation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import com.dnapayments.mp.utils.navigation.drawer_navigation.DrawerNavigator
import com.dnapayments.mp.utils.navigation.drawer_navigation.DrawerScreen

public val LocalDrawerNavigatorSaver: ProvidableCompositionLocal<DrawerNavigatorSaver<*>> =
    staticCompositionLocalOf { defaultDrawerNavigatorSaver() }

public fun interface DrawerNavigatorSaver<Saveable : Any> {
    public fun saver(
        initialScreens: List<DrawerScreen>,
        key: String,
        stateHolder: SaveableStateHolder,
        disposeBehavior: NavigatorDisposeBehavior,
        parent: DrawerNavigator?
    ): Saver<DrawerNavigator, Saveable>
}

/**
 * Default Navigator Saver expect that on Android, your screens can be saved, by default
 * all [Screen]s are Java Serializable, this means that by default, if you only pass primitive types
 * or Java Serializable types, it will restore your screen properly.
 * If you want use Android Parcelable instead, you can, you just need to implement the Parcelable interface
 * and all types should be parcelable as well and this Default Saver should work as well.
 * Important: When using Parcelable all types should be Parcelable as well, when using Serializable all types
 * should be serializable, you can't mix both unless the types are both Parcelable and Serializable.
 *
 * If you want to use only Parcelable and want a NavigatorSaver that forces the use Parcelable, you can use [parcelableNavigatorSaver].
 */
@OptIn(InternalVoyagerApi::class)
public fun defaultDrawerNavigatorSaver(): DrawerNavigatorSaver<Any> =
    DrawerNavigatorSaver { _, key, stateHolder, disposeBehavior, parent ->
        listSaver(
            save = { navigator -> navigator.items },
            restore = { items -> DrawerNavigator(items, key, stateHolder, disposeBehavior, parent) }
        )
    }