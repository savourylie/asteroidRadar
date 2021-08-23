package com.udacity.asteroidradar.api

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AsteroidProperty(
    val id: String,
    @Json(name = "absolute_magnitude") val absoluteMagnitude: Double,
    @Json(name = "estimated_diameter_max") val estimatedDiameterMax: Double,
    @Json(name = "is_potentially_hazardous_asteroid") val isPotentiallyHazardous: Boolean,
    @Json(name = "kilometers_per_second") val kilometersPerSecond: String,
    @Json(name = "astronomical") val astronomical: String
    ): Parcelable
