package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroid_table")
data class DatabaseAsteroid constructor(
    @PrimaryKey
    val id: String = "TEST_STRING",

    @ColumnInfo(name = "absolute_magnitude")
    val absoluteMagnitude: Double = 3.14159,

    @ColumnInfo(name = "estimated_diameter_max")
    val estimatedDiameterMax: Double = 3.14159,

    @ColumnInfo(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean = true,

    @ColumnInfo(name = "kilometer_per_second")
    val kilometerPerSecond: String = "TEST_STRING",

    @ColumnInfo(name = "astronomical")
    val astronomical: String = "TEST_STRING"
)