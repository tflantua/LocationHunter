package com.bijgepast.locationhunter.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "VisitedLocations",
    primaryKeys = ["UserID", "LocationID"],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = arrayOf("ID"),
        childColumns = arrayOf("UserID")
    ),
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = arrayOf("ID"),
            childColumns = arrayOf("LocationID")
        )]
)
data class VisitedLocationsEntity(
    @ColumnInfo(name = "LocationID") val locationID: Int,
    @ColumnInfo(name = "UserID") val userID: Int,
    @ColumnInfo(name = "Visited") val visited: Boolean
)