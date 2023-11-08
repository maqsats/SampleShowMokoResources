package com.dna.payments.kmm.utils.maps.geo

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