package com.dnapayments.mp.utils.maps.geo

import com.dnapayments.mp.utils.maps.geo.ExtendedLocation
import com.dnapayments.mp.utils.maps.geo.LatLng
import kotlinx.coroutines.flow.Flow

actual class LocationTracker {

    actual suspend fun startTracking() {
    }

    actual fun stopTracking() {
    }

    actual fun getLocationsFlow(): Flow<LatLng> {
        TODO("Not yet implemented")
    }

    actual fun getExtendedLocationsFlow(): Flow<ExtendedLocation> {
        TODO("Not yet implemented")
    }

}