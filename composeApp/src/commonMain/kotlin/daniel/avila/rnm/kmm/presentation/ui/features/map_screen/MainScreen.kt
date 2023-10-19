package daniel.avila.rnm.kmm.presentation.ui.features.map_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.utils.maps.CameraPosition
import daniel.avila.rnm.kmm.utils.maps.GoogleMaps
import daniel.avila.rnm.kmm.utils.maps.LatLong

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        item(key = "GoogleMaps") {
            GoogleMaps(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(horizontal = 16.dp),
                markers = arrayListOf(),
                cameraPosition = CameraPosition(
                    target = LatLong(
                        43.238949,
                        76.889709
                    ),
                    zoom = 14f
                ),
            )
        }
    }
}