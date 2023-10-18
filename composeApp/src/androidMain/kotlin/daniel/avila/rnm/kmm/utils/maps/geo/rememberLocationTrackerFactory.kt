package daniel.avila.rnm.kmm.utils.maps.geo

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationRequest
import daniel.avila.rnm.kmm.utils.permissions.PermissionsController

@Composable
actual fun rememberLocationTrackerFactory(accuracy: LocationTrackerAccuracy): LocationTrackerFactory {
    val context: Context = LocalContext.current
    return remember(context) {
        object : LocationTrackerFactory {
            override fun createLocationTracker(): LocationTracker {
                return LocationTracker(
                    priority = accuracy.toPriority()
                )
            }

            override fun createLocationTracker(
                permissionsController: PermissionsController
            ): LocationTracker {
                return LocationTracker(
                    priority = accuracy.toPriority()
                )
            }
        }
    }
}

private fun LocationTrackerAccuracy.toPriority(): Int {
    return when (this) {
        LocationTrackerAccuracy.Best -> LocationRequest.PRIORITY_HIGH_ACCURACY
        LocationTrackerAccuracy.Medium -> LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        LocationTrackerAccuracy.LowPower -> LocationRequest.PRIORITY_LOW_POWER
    }
}