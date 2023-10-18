/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package daniel.avila.rnm.kmm.utils.maps.geo

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize


@Parcelize
data class Altitude(
    val altitudeMeters: Double,
    val altitudeAccuracyMeters: Double?
) : Parcelable
