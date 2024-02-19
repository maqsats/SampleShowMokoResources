package com.dnapayments.mp.utils.navigation.lifecycle

import com.dnapayments.mp.utils.navigation.drawer_navigation.DrawerNavigator

public interface DrawerNavigatorDisposable {
    public fun onDispose(navigator: DrawerNavigator)
}