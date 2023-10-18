package daniel.avila.rnm.kmm.presentation.ui.features.exchange_places

import cafe.adriel.voyager.core.model.ScreenModel
import daniel.avila.rnm.kmm.utils.maps.geo.LocationTracker
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.core.component.KoinComponent

class TrackerViewModel(private val locationTracker: LocationTracker) : ScreenModel, KoinComponent {

    // Use locationTracker in common code
    suspend fun startLocationTracking() {
        // Access properties or methods from locationTracker
        locationTracker.startTracking()
    }

    fun stopLocationTracking() {
        locationTracker.stopTracking()
    }

    suspend fun observeLocations() {
        locationTracker.getLocationsFlow()
            .distinctUntilChanged()
            .collect { println("new location: $it") }
    }

    // Add other methods as needed
}