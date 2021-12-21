package com.bijgepast.locationhunter.database.dao

import androidx.room.*
import com.bijgepast.locationhunter.database.entities.HintsEntity
import com.bijgepast.locationhunter.database.entities.UserEntity

@Dao
interface HintsDao {
    @Insert
    fun insertHint(vararg hintEntity: HintsEntity)

    @Delete
    fun delete(hintsEntity: HintsEntity)

    @Query("SELECT COUNT(id) FROM Hints WHERE LocationID = :locationId ")
    fun getTotalHints(locationId: Int): Int

    @Query("SELECT * FROM Hints INNER JOIN UnlockedHints WHERE Hints.LocationID = :locationId AND UnlockedHints.UserID = :userId AND UnlockedHints.unlocked > 0")
    fun getUnlockedHints(locationId: Int, userId: Int): HintsEntity
}