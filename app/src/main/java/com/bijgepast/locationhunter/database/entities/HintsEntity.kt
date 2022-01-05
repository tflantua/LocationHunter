package com.bijgepast.locationhunter.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "Hints",
    primaryKeys = ["ID"],
    foreignKeys = [ForeignKey(
        entity = LocationEntity::class,
        parentColumns = arrayOf("ID"),
        childColumns = arrayOf("LocationID")
    )]
)
data class HintsEntity(
    @ColumnInfo(name = "ID") val ID: Int,
    @ColumnInfo(name = "LocationID") val locationID: Int,
    @ColumnInfo(name = "Description") val description: String,
    @ColumnInfo(name = "Cost") val cost: Int
): BaseEntity