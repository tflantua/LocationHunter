package com.bijgepast.locationhunter.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bijgepast.locationhunter.models.BaseModel

@Entity(tableName = "Location")
data class LocationEntity(
    @PrimaryKey val ID: Int,
    @ColumnInfo(name = "North") val north: Double,
    @ColumnInfo(name = "East") val east: Double,
    @ColumnInfo(name = "Riddle") val riddle: String,
    @ColumnInfo(name = "RiddleName") val riddleName: String,
    @ColumnInfo(name = "LocationName") val locationName: String,
    @ColumnInfo(name = "Points") val points: Int,
    @ColumnInfo(name = "Difficulty") val difficulty: Int
): BaseEntity {
    override fun getModel(): BaseModel {
        TODO("Not yet implemented")
    }
}