package com.bijgepast.locationhunter.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bijgepast.locationhunter.database.entities.UnlockedHintsEntity

@Dao
interface UnlockedHintsDao : BaseDao<UnlockedHintsEntity> {

    @Query("UPDATE UnlockedHints SET unlocked = :unlocked WHERE HintsID = :hintId AND UserID = :userId")
    fun updateUnlocked(unlocked: Boolean, hintId: Int, userId: Int)

    @Query("SELECT * FROM UnlockedHints WHERE HintsID = :hintId AND UserID = :userId")
    fun getUnlockedHint(hintId: Int, userId: Int): UnlockedHintsEntity

    @Query("SELECT unlocked FROM UnlockedHints WHERE HintsID = :hintId AND UserID = :userId")
    fun getUnlocked(hintId: Int, userId: Int): Boolean
}