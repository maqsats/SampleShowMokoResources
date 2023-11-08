package com.dna.payments.kmm.utils.maps.geo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.dna.payments.kmm.utils.permissions.PermissionsController
import platform.CoreLocation.CLLocationAccuracy
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.CoreLocation.kCLLocationAccuracyKilometer
import platform.CoreLocation.kCLLocationAccuracyReduced

@Composable
actual fun rememberLocationTrackerFactory(accuracy: LocationTrackerAccuracy): LocationTrackerFactory {
    return remember {
        object : LocationTrackerFactory {
            override fun createLocationTracker(): LocationTracker {
                return LocationTracker(
                    accuracy = accuracy.toIosAccuracy()
                )
            }

            override fun createLocationTracker(
                permissionsController: PermissionsController
            ): LocationTracker {
                return LocationTracker(
                    accuracy = accuracy.toIosAccuracy()
                )
            }
        }
    }
}

private fun LocationTrackerAccuracy.toIosAccuracy(): CLLocationAccuracy {
    return when (this) {
        LocationTrackerAccuracy.Best -> kCLLocationAccuracyBest
        LocationTrackerAccuracy.Medium -> kCLLocationAccuracyKilometer
        LocationTrackerAccuracy.LowPower -> kCLLocationAccuracyReduced
    }
}