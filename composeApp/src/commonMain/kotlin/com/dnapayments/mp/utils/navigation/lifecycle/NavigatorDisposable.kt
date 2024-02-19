package com.dnapayments.mp.utils.navigation.lifecycle

import com.dnapayments.mp.utils.navigation.Navigator

public interface NavigatorDisposable {
    public fun onDispose(navigator: Navigator)
}