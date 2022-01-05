package com.bijgepast.locationhunter.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "UnlockedHints",
    primaryKeys = ["UserID", "HintsID"],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = arrayOf("ID"),
        childColumns = arrayOf("UserID"),
    ),
        ForeignKey(
            entity = HintsEntity::class,
            parentColumns = arrayOf("ID"),
            childColumns = arrayOf("HintsID")
        )]
)
data class UnlockedHintsEntity(
    @ColumnInfo(name = "UserID") val userID: Int,
    @ColumnInfo(name = "HintsID") val hintsID: Int,
    @ColumnInfo(name = "unlocked") val unlocked: Boolean,
): BaseEntity