package com.bijgepast.locationhunter.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bijgepast.locationhunter.database.entities.LocationEntity

@Dao
interface LocationDao : BaseDao<LocationEntity> {

    @Query("SELECT * FROM Location")
    fun getLocations(): List<LocationEntity>

    @Query("SELECT * FROM Location WHERE ID = :id")
    fun getLocation(id: Int): LocationEntity?
}