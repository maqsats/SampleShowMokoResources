package daniel.avila.rnm.kmm.utils.maps.geo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import platform.CoreLocation.CLLocationAccuracy
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLLocationAccuracyBest

actual class LocationTracker(
    accuracy: CLLocationAccuracy = kCLLocationAccuracyBest
) {
    private val locationsChannel = Channel<LatLng>(Channel.BUFFERED)
    private val extendedLocationsChannel = Channel<ExtendedLocation>(Channel.BUFFERED)
    private val trackerScope = CoroutineScope(Dispatchers.Main)
    private val tracker = Tracker(
        locationsChannel = locationsChannel,
        extendedLocationsChannel = extendedLocationsChannel,
        scope = trackerScope
    )
    private val locationManager = CLLocationManager().apply {
        delegate = tracker
        desiredAccuracy = accuracy
    }

    actual suspend fun startTracking() {
        locationManager.startUpdatingLocation()
    }

    actual fun stopTracking() {
        locationManager.stopUpdatingLocation()
    }

    actual fun getLocationsFlow(): Flow<LatLng> {
        return channelFlow {
            val sendChannel = channel
            val job = launch {
                while (isActive) {
                    val latLng = locationsChannel.receive()
                    sendChannel.send(latLng)
                }
            }

            awaitClose { job.cancel() }
        }
    }

    actual fun getExtendedLocationsFlow(): Flow<ExtendedLocation> {
        return channelFlow {
            val sendChannel = channel
            val job = launch {
                while (isActive) {
                    val extendedLocation = extendedLocationsChannel.receive()
                    sendChannel.send(extendedLocation)
                }
            }

            awaitClose { job.cancel() }
        }
    }
}