package daniel.avila.rnm.kmm.utils.maps.geo

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner

@Suppress("FunctionNaming")
@Composable
actual fun BindLocationTrackerEffect(locationTracker: LocationTracker) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val context: Context = LocalContext.current

    LaunchedEffect(locationTracker, lifecycleOwner, context) {
        locationTracker.bind(lifecycleOwner.lifecycle, context)
    }
}