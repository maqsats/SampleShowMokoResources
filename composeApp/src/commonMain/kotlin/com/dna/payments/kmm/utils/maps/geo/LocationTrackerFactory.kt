package com.dna.payments.kmm.utils.maps.geo

import androidx.compose.runtime.Composable
import com.dna.payments.kmm.utils.permissions.PermissionsController

interface LocationTrackerFactory {
    fun createLocationTracker(): LocationTracker
    fun createLocationTracker(permissionsController: PermissionsController): LocationTracker
}

enum class LocationTrackerAccuracy {
    Best,
    Medium,
    LowPower
}

@Composable
expect fun rememberLocationTrackerFactory(accuracy: LocationTrackerAccuracy): LocationTrackerFactory