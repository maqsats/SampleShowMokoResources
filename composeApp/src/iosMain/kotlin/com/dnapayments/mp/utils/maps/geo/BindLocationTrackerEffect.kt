package com.dnapayments.mp.utils.maps.geo

import androidx.compose.runtime.Composable

// on iOS side we should not do anything to prepare LocationTracker to work
@Suppress("FunctionNaming")
@Composable
actual fun BindLocationTrackerEffect(locationTracker: LocationTracker) = Unit