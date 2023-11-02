package daniel.avila.rnm.kmm.utils.maps

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import daniel.avila.rnm.kmm.MR
import dev.icerock.moko.resources.compose.painterResource

@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    isControlsVisible: Boolean,
    onMarkerClick: ((MapMarker) -> Unit)?,
    onMapClick: ((LatLong) -> Unit)?,
    onMapLongClick: ((LatLong) -> Unit)?,
    markers: List<MapMarker>?,
    cameraPosition: CameraPosition?,
    cameraPositionLatLongBounds: CameraPositionLatLongBounds?,
    polyLine: List<LatLong>?
) {

    val cameraPositionState = rememberCameraPositionState()
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings()
        )
    }
    val properties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = false,
                minZoomPreference = 10f,
                maxZoomPreference = 20f,
            )
        )
    }

    LaunchedEffect(cameraPosition) {
        cameraPosition?.let { cameraPosition ->
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        cameraPosition.target.latitude,
                        cameraPosition.target.longitude
                    ),
                    cameraPosition.zoom
                ),
                durationMs = 300
            )
        }
    }

    LaunchedEffect(cameraPositionLatLongBounds) {
        cameraPositionLatLongBounds?.let {

            val latLngBounds = LatLngBounds.builder().apply {
                it.coordinates.forEach { latLong ->
                    include(LatLng(latLong.latitude, latLong.longitude))
                }
            }.build()

            cameraPositionState.move(
                CameraUpdateFactory.newLatLngBounds(latLngBounds, it.padding)
            )
        }
    }

    Box(Modifier.fillMaxSize()) {

        GoogleMap(
            cameraPositionState = cameraPositionState,
            modifier = modifier,
            uiSettings = uiSettings,
            properties = properties,
            onMapClick = { latLng: LatLng ->
                onMapClick?.let { nativeFun ->
                    nativeFun(LatLong(latLng.latitude, latLng.longitude))
                }
            },

            ) {
            markers?.forEach { marker ->
                MarkerComposable(
                    state = rememberMarkerState(
                        key = marker.key,
                        position = LatLng(marker.position.latitude, marker.position.longitude)
                    ),
                    alpha = marker.alpha,
                ) {
                    Image(
                        painter = painterResource(MR.images.light),
                        contentDescription = null,
                        modifier = Modifier
                            .width(67.dp)
                            .height(67.dp)
                    )
                }
            }

            polyLine?.let { polyLine ->
                Polyline(
                    points = List(polyLine.size) {
                        val latLong = polyLine[it]
                        LatLng(latLong.latitude, latLong.longitude)
                    },
                    color = Color(0XFF1572D5),
                    width = 16f
                )
                Polyline(
                    points = List(polyLine.size) {
                        val latLong = polyLine[it]
                        LatLng(latLong.latitude, latLong.longitude)
                    },
                    color = Color(0XFF00AFFE),
                    width = 8f
                )
            }
        }
    }
}
