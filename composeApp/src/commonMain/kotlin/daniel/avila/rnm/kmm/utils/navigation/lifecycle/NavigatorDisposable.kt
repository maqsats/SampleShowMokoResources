package daniel.avila.rnm.kmm.utils.navigation.lifecycle

import daniel.avila.rnm.kmm.utils.navigation.Navigator

public interface NavigatorDisposable {
    public fun onDispose(navigator: Navigator)
}