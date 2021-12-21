package com.bijgepast.locationhunter.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bijgepast.locationhunter.database.entities.UnlockedHintsEntity

@Dao
interface UnlockedHintsDao {
    @Insert
    fun insertUnlockedHints(vararg unlockedHintsEntity: UnlockedHintsEntity)

    @Delete
    fun delete(unlockedHintsEntity: UnlockedHintsEntity)

    @Query("UPDATE UnlockedHints SET unlocked = :unlocked WHERE HintsID = :hintId AND UserID = :userId")
    fun updateUnlocked(unlocked: Boolean, hintId: Int, userId: Int)
}