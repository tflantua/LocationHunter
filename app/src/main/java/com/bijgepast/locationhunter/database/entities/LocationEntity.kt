package com.bijgepast.locationhunter.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Location")
data class LocationEntity(
    @PrimaryKey val ID: Int,
    @ColumnInfo(name = "North") val north: Double,
    @ColumnInfo(name = "East") val east: Double,
    @ColumnInfo(name = "Riddle") val riddle: String,
    @ColumnInfo(name = "RiddleName") val riddleName: String,
    @ColumnInfo(name = "LocationName") val locationName: String,
)