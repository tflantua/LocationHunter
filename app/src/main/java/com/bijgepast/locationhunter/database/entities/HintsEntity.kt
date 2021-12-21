package com.bijgepast.locationhunter.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Hints",
    foreignKeys = [ForeignKey(
        entity = LocationEntity::class,
        parentColumns = arrayOf("ID"),
        childColumns = arrayOf("LocationID")
    )]
)
data class HintsEntity(
    @PrimaryKey val ID: Int,
    @ColumnInfo(name = "LocationID") val locationID: Int,
    @ColumnInfo(name = "Description") val description: String
)