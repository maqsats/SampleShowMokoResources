package daniel.avila.rnm.kmm.presentation.ui.features.exchange_places

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import daniel.avila.rnm.kmm.utils.maps.geo.BindLocationTrackerEffect
import daniel.avila.rnm.kmm.utils.maps.geo.LocationTrackerAccuracy
import daniel.avila.rnm.kmm.utils.maps.geo.rememberLocationTrackerFactory
import daniel.avila.rnm.kmm.utils.permissions.DeniedAlwaysException
import daniel.avila.rnm.kmm.utils.permissions.DeniedException
import daniel.avila.rnm.kmm.utils.permissions.Permission
import daniel.avila.rnm.kmm.utils.permissions.PermissionsController
import daniel.avila.rnm.kmm.utils.permissions.compose.BindEffect
import daniel.avila.rnm.kmm.utils.permissions.compose.PermissionsControllerFactory
import daniel.avila.rnm.kmm.utils.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ExchangePlaces(modifier: Modifier = Modifier) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController =
        remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)

    val locationTracker = rememberLocationTrackerFactory(accuracy = LocationTrackerAccuracy.Best)
        .createLocationTracker(controller)

    BindLocationTrackerEffect(locationTracker)

    var locationLatLng by remember { mutableStateOf("") }
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        Box(modifier = modifier.fillMaxWidth().wrapContentWidth()) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        println("requesting permissions")
                        try {
                            controller.providePermission(Permission.LOCATION)
                            delay(2000)
                            locationTracker.startTracking()
                            locationTracker.getLocationsFlow().collect {
                                locationLatLng = "${it.latitude}, ${it.longitude}"
                            }
                            println("permissions granted")
                        } catch (deniedAlways: DeniedAlwaysException) {
                            println("permissions denied always")
                        } catch (denied: DeniedException) {
                            println("permissions denied")
                        }
                    }
                }
            ) {
            }
        }
        Text(text = locationLatLng, modifier = Modifier.fillMaxWidth())
    }
}