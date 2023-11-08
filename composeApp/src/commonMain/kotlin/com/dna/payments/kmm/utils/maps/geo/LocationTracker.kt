package com.dna.payments.kmm.utils.maps.geo

import kotlinx.coroutines.flow.Flow

expect class LocationTracker {

    suspend fun startTracking() // can be suspended for request permission
    fun stopTracking()

    fun getLocationsFlow(): Flow<LatLng>

    fun getExtendedLocationsFlow(): Flow<ExtendedLocation>
}
