package com.dna.payments.kmm.utils.navigation.lifecycle

import com.dna.payments.kmm.utils.navigation.Navigator

public interface NavigatorDisposable {
    public fun onDispose(navigator: Navigator)
}