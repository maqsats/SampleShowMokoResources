/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.dnapayments.mp.utils.maps.geo

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
class Location(
    val coordinates: LatLng,
    val coordinatesAccuracyMeters: Double
) : Parcelable
