package com.dnapayments.mp.utils.maps

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
}