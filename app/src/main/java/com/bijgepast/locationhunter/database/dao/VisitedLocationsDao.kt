package com.bijgepast.locationhunter.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bijgepast.locationhunter.database.entities.VisitedLocationsEntity

@Dao
interface VisitedLocationsDao {
    @Insert
    fun insertVisitedLocation(vararg visitedLocationsEntity: VisitedLocationsEntity)

    @Delete
    fun delete(visitedLocationsEntity: VisitedLocationsEntity)

    @Query("UPDATE VisitedLocations SET Visited = :visited WHERE LocationID = :locationId AND UserID = :userId")
    fun updateVisited(visited: Boolean, locationId: Int, userId: Int)
}