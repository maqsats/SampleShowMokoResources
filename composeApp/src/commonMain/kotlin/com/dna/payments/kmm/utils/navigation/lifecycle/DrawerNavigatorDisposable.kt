package com.dna.payments.kmm.utils.navigation.lifecycle

import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerNavigator

public interface DrawerNavigatorDisposable {
    public fun onDispose(navigator: DrawerNavigator)
}